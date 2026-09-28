package com.rebbxyz.client.render;

import com.rebbxyz.client.RebbxyzClient;
import com.rebbxyz.client.module.ColorSetting;
import com.rebbxyz.client.module.StorageFinder;
import com.rebbxyz.client.module.StorageType;
import com.rebbxyz.client.module.SpawnerFinder;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public final class WorldHighlighter {
    private static final List<Target> targets = new ArrayList<>();
    private static int scanCooldown;

    private record Target(BlockPos pos, ColorSetting color, float width, boolean tracer) {}

    private WorldHighlighter() {}

    public static void register() {
        WorldRenderEvents.END_MAIN.register(WorldHighlighter::render);
    }

    private static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null || context.matrices() == null || context.consumers() == null) return;

        if (scanCooldown-- <= 0) {
            scanCooldown = 8;
            scan(client);
        }

        Vec3d camera = client.gameRenderer.getCamera().getPos();
        MatrixStack matrices = context.matrices();
        VertexConsumerProvider consumers = context.consumers();

        for (Target target : targets) {
            double dx = target.pos.getX() + 0.5 - camera.x;
            double dy = target.pos.getY() - camera.y;
            double dz = target.pos.getZ() + 0.5 - camera.z;

            matrices.push();
            VoxelShape shape = client.world.getBlockState(target.pos).getOutlineShape(client.world, target.pos);
            VertexConsumer outline = consumers.getBuffer(RenderLayer.getLines());
            VertexRendering.drawOutline(matrices, outline, shape, dx, dy, dz, target.color.argb(), target.width);

            if (target.tracer) {
                drawTracer(matrices, outline, 0, 0, 0, dx, dy + 0.5, dz, target.color.argb());
            }
            matrices.pop();
        }
    }

    private static void scan(MinecraftClient client) {
        targets.clear();
        StorageFinder storage = RebbxyzClient.MODULES.storageFinder();
        SpawnerFinder spawner = RebbxyzClient.MODULES.spawnerFinder();

        if (!storage.enabled() && !spawner.enabled()) return;

        BlockPos origin = client.player.getBlockPos();
        int maxRange = Math.max(
                storage.enabled() ? storage.range() : 0,
                spawner.enabled() ? spawner.range() : 0
        );

        BlockPos.Mutable cursor = new BlockPos.Mutable();
        int r = maxRange;

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    if (x*x + y*y + z*z > r*r) continue;
                    cursor.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    BlockState state = client.world.getBlockState(cursor);
                    Block block = state.getBlock();

                    if (storage.enabled() && origin.getSquaredDistance(cursor) <= storage.range() * storage.range()) {
                        StorageType type = typeOf(block);
                        if (type != null) {
                            targets.add(new Target(cursor.toImmutable(), storage.color(type), storage.lineWidth(), storage.tracer(type)));
                            continue;
                        }
                    }

                    if (spawner.enabled()
                            && origin.getSquaredDistance(cursor) <= spawner.range() * spawner.range()
                            && block == Blocks.SPAWNER) {
                        targets.add(new Target(cursor.toImmutable(), spawner.color(), spawner.lineWidth(), spawner.tracer()));
                    }
                }
            }
        }
    }

    private static StorageType typeOf(Block block) {
        if (block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST) return StorageType.CHEST;
        if (block == Blocks.SHULKER_BOX
                || block == Blocks.WHITE_SHULKER_BOX || block == Blocks.ORANGE_SHULKER_BOX
                || block == Blocks.MAGENTA_SHULKER_BOX || block == Blocks.LIGHT_BLUE_SHULKER_BOX
                || block == Blocks.YELLOW_SHULKER_BOX || block == Blocks.LIME_SHULKER_BOX
                || block == Blocks.PINK_SHULKER_BOX || block == Blocks.GRAY_SHULKER_BOX
                || block == Blocks.LIGHT_GRAY_SHULKER_BOX || block == Blocks.CYAN_SHULKER_BOX
                || block == Blocks.PURPLE_SHULKER_BOX || block == Blocks.BLUE_SHULKER_BOX
                || block == Blocks.BROWN_SHULKER_BOX || block == Blocks.GREEN_SHULKER_BOX
                || block == Blocks.RED_SHULKER_BOX || block == Blocks.BLACK_SHULKER_BOX) {
            return StorageType.SHULKER;
        }
        if (block == Blocks.DROPPER) return StorageType.DROPPER;
        return null;
    }

    private static void drawTracer(MatrixStack matrices, VertexConsumer consumer,
                                   double x1, double y1, double z1,
                                   double x2, double y2, double z2, int argb) {
        MatrixStack.Entry entry = matrices.peek();
        consumer.vertex(entry, (float)x1, (float)y1, (float)z1).color(argb);
        consumer.vertex(entry, (float)x2, (float)y2, (float)z2).color(argb);
    }
}

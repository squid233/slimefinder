/*
 * MIT License
 *
 * Copyright (c) 2023 squid233
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package io.squid233.slimefinder.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author squid233
 * @since 0.1.0
 */
@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract WorldChunk getClientChunk();

    @Shadow
    @Nullable
    protected abstract ServerWorld getServerWorld();

    @Inject(at = @At("RETURN"), method = "getLeftText")
    private void getLeftText(CallbackInfoReturnable<List<String>> cir) {
        if (!getClientChunk().isEmpty()) {
            final ServerWorld world = getServerWorld();
            final Entity entity = client.cameraEntity;
            if (world != null && entity != null) {
                ChunkPos chunkPos = new ChunkPos(entity.getBlockPos());
                cir.getReturnValue().add(String.format("[Slime Finder] Is slime chunk: %b",
                    ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, world.getSeed(), 987234911L).nextInt(10) == 0
                ));
            }
        }
    }
}

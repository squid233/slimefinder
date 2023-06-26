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

package io.github.squid233.slimefinder.mixin.client;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

/**
 * @author squid233
 * @since 0.1.0
 */
@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Inject(at = @At(value = "INVOKE_ASSIGN", ordinal = 8, target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), method = "getLeftText", locals = LocalCapture.CAPTURE_FAILHARD)
    private void getLeftText(CallbackInfoReturnable<List<String>> cir, String string, IntegratedServer integratedServer, ClientConnection clientConnection, float f, float g, BlockPos blockPos, Entity entity, Direction direction, String string2, ChunkPos chunkPos, World world, LongSet longSet, List<String> list, String string3, WorldChunk worldChunk, int i, int j, int k) {
        if (world instanceof StructureWorldAccess access) {
            final ChunkPos pos = worldChunk.getPos();
            list.add(String.format("[Slime Finder] Is slime chunk: %b",
                ChunkRandom.getSlimeRandom(pos.x, pos.z, access.getSeed(), 987234911L).nextInt(10) == 0
            ));
        }
    }
}

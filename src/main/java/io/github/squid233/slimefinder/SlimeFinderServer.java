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

package io.github.squid233.slimefinder;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class SlimeFinderServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
            sendSeedPacket(server, handler.player));
        ServerPlayNetworking.registerGlobalReceiver(SlimeFinder.SEED_PACKET_ID, (server, player, handler, buf, responseSender) ->
            sendSeedPacket(server, player));
    }

    private void sendSeedPacket(MinecraftServer server, ServerPlayerEntity player) {
        final PacketByteBuf buf = PacketByteBufs.create();
        buf.writeLong(server.getWorld(World.OVERWORLD).getSeed());
        ServerPlayNetworking.send(player, SlimeFinder.SEED_PACKET_ID, buf);
    }
}

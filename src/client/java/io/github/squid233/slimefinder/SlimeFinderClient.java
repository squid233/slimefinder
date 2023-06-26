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

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

/**
 * @author squid233
 * @since 0.1.0
 */
public final class SlimeFinderClient implements ClientModInitializer {
    public static Long seed = null;

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SlimeFinder.SEED_PACKET_ID, (client, handler, buf, responseSender) -> {
            seed = buf.readLong();
        });
    }
}

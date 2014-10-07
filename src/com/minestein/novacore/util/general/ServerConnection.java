package com.minestein.novacore.util.general;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.minestein.novacore.Core;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public final class ServerConnection {

    /**
     * Cannot construct new ServerConnection object.
     */
    private ServerConnection() { }

    /**
     * Connects the toSend player to the server via BungeeCord.
     * @param toSend The player to send.
     * @param server The server's display name to send the player to.
     */
    public static void connect(Player toSend, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        toSend.sendPluginMessage(Core.plugin, "BungeeCord", out.toByteArray());
    }
}

package com.minestein.novacore.util.general;

import com.minestein.novacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class ChatUtil {

    /**
     * Broadcasts a message to all players on the server.
     * @param message The message to broadcast.
     */
    public static void broadcast(String message) {
        Bukkit.broadcastMessage(Core.getPrefix()+"§6"+message);
    }

    /**
     * Broadcasts a message to all players who have a permission on the server.
     * @param message The message to broadcast.
     * @param permission The permission to check for.
     */
    public static void broadcastPermission(String message, String permission) {
        Bukkit.broadcast(Core.getPrefix()+"§6"+message, permission);
    }

    /**
     * Broadcasts a message to all players in the array.
     * @param message The message to broadcast.
     * @param players The array to iterate through.
     */
    public static void broadcast(String message, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].sendMessage(Core.getPrefix()+"§6"+message);
        }
    }

    /**
     * Broadcasts a message to all players in the array that have the permission.
     * @param message The message to broadcast.
     * @param permission The permission to check for.
     * @param players The array to iterate through.
     */
    public static void broadcastPermission(String message, String permission, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].hasPermission(permission)) {
                players[i].sendMessage(Core.getPrefix()+"§6"+message);
            }
        }
    }
}

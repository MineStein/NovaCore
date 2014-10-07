package com.minestein.novacore.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.minestein.novacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Hub implements CommandExecutor {

    /**
     * The list of people already being sent to the hub.
     */
    public static ArrayList<String> alreadySending = new ArrayList<>();

    /**
     * @param sender  The thing that sent the command.
     * @param command The command sent.
     * @param s       The command's label.
     * @param strings The arguments to the command.
     * @return ignore
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Core.getPrefix() + "§4Only players can exit to hub!");
            return true;
        } else {
            final Player p = (Player) sender;

            if (alreadySending.contains(p.getName())) {
                p.sendMessage(Core.getPrefix() + "§4You are already going to the hub!");
                return true;
            }

            alreadySending.add(p.getName());

            String[] messages = new String[]{
                    "Through the portal!", "They aren't gonna make it!",
                    "RUUUNNNNNN!", "It's a trap!", "Don't do it!"
            };
            p.sendMessage(Core.getPrefix() + "§bSending you to the hub! §b§o" + messages[Core.random.nextInt(messages.length)]);

            Bukkit.getScheduler().runTaskLater(Core.plugin, new Runnable() {
                @Override
                public void run() {
                    alreadySending.remove(p.getName());

                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF("lobby");
                    p.sendPluginMessage(Core.plugin, "BungeeCord", out.toByteArray());
                }
            }, 30);
        }
        return true;
    }
}
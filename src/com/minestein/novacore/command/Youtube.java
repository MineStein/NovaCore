package com.minestein.novacore.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Youtube implements CommandExecutor {

    /**
     * The youtube prefix for the Youtube command.
     */
    final String PREFIX = "§fYou§4§lTube§8> §f";

    /**
     *
     * @param sender The thing that sent the command.
     * @param command The command that was sent.
     * @param label The label of the command.
     * @param args The arguments of the command.
     * @return ignore
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("youtube")) {
            if (!sender.hasPermission("youtube.command")) {
                sender.sendMessage(PREFIX+"§4You don't have permission!");
                return true;
            }

            if (args.length==0) {
                sender.sendMessage(PREFIX+"§eStream <URL> §8- §6Announce a stream");
                sender.sendMessage(PREFIX+"§eRecord <URL> §8- §6Announce a recording");
                return true;
            } else {
                if (!(sender instanceof Player)) return true;

                Player player = (Player) sender;
                if (args.length==1) {
                    String args0 = args[0];
                    if (args0.equalsIgnoreCase("record")) {
                        Bukkit.broadcastMessage(PREFIX+"§e§l" + player.getName().toUpperCase() + " §6is now recording!");
                        return true;
                    } else if (args0.equalsIgnoreCase("stream")) {
                        sender.sendMessage(PREFIX + "§4Too few arguments!");
                        return true;
                    } else {
                        sender.sendMessage(PREFIX+"§4Unknown subcommand!");
                        return true;
                    }
                } else if (args.length==2) {
                    String args0 = args[0];
                    String args1 = args[1];

                    if (args0.equalsIgnoreCase("stream")) {
                        Bukkit.broadcastMessage(PREFIX+"§e§l"+player.getName().toUpperCase()+" §6is now streaming at \n§e§l"+args1.toUpperCase()+"§6!");
                        return true;
                    } else if (args0.equalsIgnoreCase("record")) {
                        sender.sendMessage(PREFIX+"§4Too many arguments!");
                        return true;
                    } else {
                        player.sendMessage(PREFIX+"§4Unknown subcommand!");
                        return true;
                    }
                } else {
                    sender.sendMessage(PREFIX+"§4Too many arguments!");
                    return true;
                }
            }
        }
        return true;
    }
}

package com.minestein.novacore.command;

import com.minestein.novacore.Core;
import com.minestein.novacore.team.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Join implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length==0) {
            String[] text = new String[] {
                "§a§lJoin a team",
                "§9§lBLUE §8- §e§l"+Blue.getSize(),
                "§a§lGREEN §8- §e§l"+ Green.getSize(),
                "§e§lYELLOW §8- §e§l"+ Yellow.getSize(),
                "§c§lRED §8- §e§l"+ Red.getSize()
            };

            sender.sendMessage(text);
            return true;
        } else {
            String team = args[0];

            if (!(sender instanceof Player)) {
                sender.sendMessage(Core.getPrefix()+"§4You cannot join a team unless you are a player!");
                return true;
            }

            Player player = (Player) sender;

            if (team.equalsIgnoreCase("blue")) {
                Team.addMember(player);
                player.sendMessage(Core.getPrefix()+"§6You joined §9§lBLUE§6.");
                return true;
            } else if (team.equalsIgnoreCase("green")) {
                return true;
            } else if (team.equalsIgnoreCase("yellow")) {
                return true;
            } else if (team.equalsIgnoreCase("red")) {
                return true;
            } else {
                sender.sendMessage(Core.getPrefix()+"§4Unknown team. Do §c/join §4for a list of teams.");
                return true;
            }
        }
    }
}

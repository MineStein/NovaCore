package com.minestein.novacore.command;

import com.minestein.novacore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Guide implements CommandExecutor {

    public static Set<String> alreadyGuide = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (alreadyGuide.contains(p.getName())) {
                p.sendMessage(Core.getPrefix()+"§4You already have a guide!");
                return true;
            } else {
                p.sendMessage(Core.getPrefix()+"§bYou have received a guide for §"+Core.validColorCodes[Core.getRandom().nextInt(Core.validColorCodes.length)]+"§l"+Core.getSoftPrefix());
                p.getInventory().addItem(Core.getGuideItem());

                alreadyGuide.add(p.getName());
                return true;
            }
        } else {
            sender.sendMessage("You cannot use the guide command!");
            return true;
        }
    }
}

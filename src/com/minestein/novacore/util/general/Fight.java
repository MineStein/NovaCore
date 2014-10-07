package com.minestein.novacore.util.general;

import com.minestein.novacore.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Fight {

    /**
     * Players in the fight.
     */
    Player damager, damaged;

    /**
     * The fights occurring right now.
     */
    public static ArrayList<Fight> fights = new ArrayList<>();

    /**
     * Creates a new fight between two players.
     * @param damager The person who damaged the damager.
     * @param damaged The person who was damaged by the damager.
     */
    public Fight(Player damager, Player damaged) {
        this.damager = damager;
        this.damaged = damaged;

        for (Fight currentFights : fights) {
            if (currentFights.getDamager() == damager || currentFights.getDamaged() == damaged) {
                return;
            } else {
                damager.sendMessage(Core.getPrefix()+"§6You engaged in a fight with §e§l"+damaged.getName().toUpperCase()+"§6!");
                damaged.sendMessage(Core.getPrefix()+"§e§l"+damager.getName().toUpperCase()+" §6engaged in a fight with you!");

                fights.add(new Fight(damager, damaged));
            }
        }
    }

    /**
     * Gets the person who damaged the damaged
     * @return The damager
     */
    public Player getDamager() {
        return damager;
    }

    /**
     * Gets the person who was damaged by the damager
     * @return The damaged
     */
    public Player getDamaged() {
        return damaged;
    }
}

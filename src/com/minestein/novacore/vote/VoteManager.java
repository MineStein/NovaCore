package com.minestein.novacore.vote;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class VoteManager {

    static HashMap<Integer, Integer> votes = new HashMap<>();
    static ArrayList<VoteOption> registeredVoteOptions = new ArrayList<>();

    public static HashMap<Integer, Integer> getVotes() {
        return votes;
    }

    public static ArrayList<VoteOption> getRegisteredVoteOptions() {
        return registeredVoteOptions;
    }

    public static void registerVoteOption(VoteOption option) {
        if (getRegisteredVoteOptions().contains(option)) {
            Bukkit.getLogger().warning("Tried to register voting option that is already registered!");
        } else if (!getRegisteredVoteOptions().contains(option)) {
            getRegisteredVoteOptions().add(option);
            Bukkit.getLogger().info("Registered voting option: "+option.getMapName()+", "+option.getWorld().getName()+", "+option.getId()+".");
        }
    }

    public static void unregisterVoteOption(VoteOption option) {
        if (getRegisteredVoteOptions().contains(option)) {
            getRegisteredVoteOptions().remove(option);
            Bukkit.getLogger().info("Unregistered voting option: "+option.getMapName()+", "+option.getWorld().getName()+", "+option.getId()+".");
        } else if (!getRegisteredVoteOptions().contains(option)) {
            Bukkit.getLogger().warning("Tried to unregister voting option that is not registered!");
        }
    }
}

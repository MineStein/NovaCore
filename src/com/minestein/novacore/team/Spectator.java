package com.minestein.novacore.team;

import com.minestein.novacore.Core;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Author: Champ.
 */
public class Spectator extends Team {

    private Core core;

    public ArrayList<String> spectators = new ArrayList<>();

    static String name;
    static HashSet<Player> members;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Spectator.name = name;
    }

    public static HashSet<Player> getMembers() {
        return members;
    }

    public static void setMembers(HashSet<Player> members) {
        Spectator.members = members;
    }

    /**
     * Creates a new team (NOTE: TeamManager.registerTeam(team) in the onEnable is required).
     *
     * @param name    The name of the new team.
     * @param members The members within the team.
     */
    public Spectator(String name, HashSet<Player> members) {
        super(name, members);
        this.name = name;
        this.members = members;

    }

    public void setSpectator(Player player) {
        spectators.add(player.getName());
        if (spectators.contains(player.getName())){
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
            player.setCanPickupItems(false);
            core.setDropItemsEnabled(false);
            player.hidePlayer(player);
            player.sendMessage(ChatColor.GREEN + "You are now in Spectator Mode.");
        }
    }

    public void removeSpectator(Player player){
        if (spectators.contains(player.getName())){
            spectators.remove(player.getName());
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public void clearSpectators(){
        spectators.clear();
    }
}
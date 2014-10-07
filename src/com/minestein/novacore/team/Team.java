package com.minestein.novacore.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Team {

    /**
     * The name of the team.
     */
    String name;
    /**
     * The members within the team.
     */
    HashSet<Player> members;

    /**
     * Creates a new team (NOTE: TeamManager.registerTeam(team) in the onEnable is required).
     *
     * @param name    The name of the new team.
     * @param members The members within the team.
     */
    public Team(String name, HashSet<Player> members) {
        this.name = name;
        this.members = members;
    }

    /**
     * Adds the player to the team.
     *
     * @param player The player to add.
     */
    public void addMember(Player player) {
        this.members.add(player);
    }

    /**
     * Removes the player from the team.
     *
     * @param player The player to remove.
     */
    public void removeMember(Player player) {
        if (!members.contains(player)) {
            Bukkit.getLogger().warning(player.getName().toUpperCase() + " is not on this team!");
            return;
        } else {
            this.members.remove(player);
        }
    }

    /**
     * Gets the name of the team.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name The new name to replace the old one.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the members in the team.
     *
     * @return The members.
     */
    public Set<Player> getMembers() {
        return members;
    }

    /**
     * Sets the members in the team.
     *
     * @param members The new members to replace the old one.
     */
    public void setMembers(HashSet<Player> members) {
        this.members = members;
    }
}

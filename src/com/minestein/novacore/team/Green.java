package com.minestein.novacore.team;

import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Green extends Team {

    static String name;
    static HashSet<Player> members;

    /**
     * Creates a new team (NOTE: TeamManager.registerTeam(team) in the onEnable is required).
     *
     * @param name    The name of the new team.
     * @param members The members within the team.
     */
    public Green(String name, HashSet<Player> members) {
        super(name, members);
        this.name = name;
        this.members = members;
    }

    public static int getSize() {
        return members.size();
    }

    public static String getTeamName() {
        return name;
    }

    public static void setTeamName(String name) {
        Green.name = name;
    }

    public static HashSet<Player> getTeamMembers() {
        return members;
    }

    public static void setTeamMembers(HashSet<Player> members) {
        Green.members = members;
    }
}

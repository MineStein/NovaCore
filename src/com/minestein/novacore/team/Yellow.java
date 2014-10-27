package com.minestein.novacore.team;

import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Yellow extends Team {

    static String name;
    static HashSet<Player> members;

    /**
     * Creates a new team (NOTE: TeamManager.registerTeam(team) in the onEnable is required).
     *
     * @param name    The name of the new team.
     * @param members The members within the team.
     */
    public Yellow(String name, HashSet<Player> members) {
        super(name, members);
        this.name = name;
        this.members = members;
    }

    public static int getSize() {
        return Yellow.members.size();
    }

    public static String getTeanName() {
        return Yellow.name;
    }

    public static void setTeanName(String name) {
       Yellow.name = name;
    }

    public static HashSet<Player> getTeamMembers() {
        return Yellow.members;
    }

    public static void setTeanMembers(HashSet<Player> members) {
        Yellow.members = members;
    }
}

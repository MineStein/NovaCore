package com.minestein.novacore.team;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class TeamManager {

    /**
     * The set of teams in the game.
     */
    static Set<Team> teams = new HashSet<>();

    /**
     * Gets the set of teams in the game.
     *
     * @return A set of teams.
     */
    public static Set<Team> getTeams() {
        return teams;
    }

    /**
     * Registers team into "teams" set.
     *
     * @param team The team to register.
     */
    public static void registerTeam(Team team) {
        getTeams().add(team);
    }
}

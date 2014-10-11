package com.minestein.novacore.team;

import org.bukkit.entity.Player;

import java.util.HashSet;

/**
 * Author: Champ.
 */
public class Spectator extends Team {

    /**
     * Creates a new team (NOTE: TeamManager.registerTeam(team) in the onEnable is required).
     *
     * @param name    The name of the new team.
     * @param members The members within the team.
     */
    public Spectator(String name, HashSet<Player> members) {
        super(name, members);
    }
}

package com.minestein.novacore.vote;

import com.minestein.novacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class VoteOption {

    String mapName;
    World world;
    Location[] spawnpoints;
    int id;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location[] getSpawnpoints() {
        return spawnpoints;
    }

    public void setSpawnpoints(Location[] spawnpoints) {
        this.spawnpoints = spawnpoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VoteOption(String mapName, World world, Location[] spawnpoints, int id) {
        if (!Core.isVotingEnabled()) {
            Bukkit.getLogger().warning("Tried to create voting option whilst voting is disabled for this gamemode!");
        } else if (Core.isVotingEnabled()) {
            if (VoteManager.getRegisteredVoteOptions().size()==8) {
                Bukkit.getLogger().warning("Tried to create voting option whilst voting menu is already full!");
            } else if (VoteManager.getRegisteredVoteOptions().size()!=8) {
                this.mapName = mapName;
                this.world = world;
                this.spawnpoints = spawnpoints;
                this.id = id;
            }
        }
    }
}

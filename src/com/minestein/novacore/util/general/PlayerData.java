package com.minestein.novacore.util.general;

import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public final class PlayerData {

    /**
     * Cannot construct new PlayerData object.
     */
    private PlayerData() { }

    /**
     * Resets any of the player data that is true.
     * @param player The player to get the data from.
     * @param health The health.
     * @param hunger The hunger.
     * @param saturation The saturation.
     * @param remainingAir The remaining air.
     */
    public void resetData(Player player, boolean health, boolean hunger, boolean saturation, boolean remainingAir) {
        if (health) {
            player.setHealth(20.0);
        }
        if (hunger) {
            player.setFoodLevel(20);
        }
        if (saturation) {
            player.setSaturation(8F);
        }
        if (remainingAir) {
            player.setRemainingAir(300);
        }
    }
}

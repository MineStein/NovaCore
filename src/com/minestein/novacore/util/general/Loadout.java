package com.minestein.novacore.util.general;

import org.bukkit.inventory.ItemStack;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Loadout {

    /**
     * The armor and inventory to be sent to a player.
     */
    ItemStack[] armor, inventory;

    /**
     * Creates a new loadout.
     * @param armor The armor in the loadout.
     * @param inventory The inventory in the loadout.
     */
    public Loadout(ItemStack[] armor, ItemStack[] inventory) {
        this.armor = armor;
        this.inventory = inventory;
    }

    /**
     * Gets the armor in the loadout.
     * @return The armor.
     */
    public ItemStack[] getArmor() {
        return this.armor;
    }

    /**
     * Sets the armor in the loadout.
     * @param armor The armor to replace the old array.
     */
    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    /**
     * Gets the inventory in the loadout.
     * @return The inventory.
     */
    public ItemStack[] getInventory() {
        return this.inventory;
    }

    /**
     * Sets the inventory in the loadout.
     * @param inventory The inventory to replace the old array.
     */
    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }
}

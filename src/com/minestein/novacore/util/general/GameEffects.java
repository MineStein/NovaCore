package com.minestein.novacore.util.general;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class GameEffects {

    /**
     * Plays a sound effect at the players location.
     *
     * @param source The source of the sound.
     * @param entity The entity that the sound will be played at.
     * @param sound  The sound to play.
     */
    public static void playSoundEffect(Player source, Entity entity, Sound sound) {
        source.playSound(entity.getLocation(), sound, 1F, 1F);
    }

    /**
     * Plays a formatted particles at the entities location.
     *
     * @param entity The entity that the particle will played at.
     * @param effect The effect to play.
     */
    public static void playFormattedParticleEffect(Entity entity, ParticleEffect effect) {
        effect.display(1, 1, 1, 20, 200, entity.getLocation(), 20);
    }
}

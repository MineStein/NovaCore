package com.minestein.novacore.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.minestein.novacore.Core;
import com.minestein.novacore.command.Hub;
import com.minestein.novacore.util.general.ChatUtil;
import com.minestein.novacore.util.general.Fight;
import com.minestein.novacore.util.general.State;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Events implements Listener {

    /**
     * Listens for interacting.
     *
     * @param event The event.
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player p = event.getPlayer();
        if (Core.getState() != State.INGAME) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
            if (event.getItem() == null) return;
            if (event.getItem().getItemMeta().getDisplayName() == null) return;

            p.performCommand("hub");
        }
    }

    /**
     * Listens for dying.
     *
     * @param event The event.
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();

        event.setDeathMessage(Core.getPrefix() + "§e§l" + event.getEntity().getName().toUpperCase() + " §6has lost!");
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setSaturation(8F);
        player.setRemainingAir(300);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        if (Bukkit.getOnlinePlayers().length - 1 <= 1) {
            player.kickPlayer("§c§lYou have lost! The game is restarting...");

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.getName().equals(player.getName())) continue;

                players.kickPlayer("§a§lYou have won! The game is restarting...");
            }

            Core.setState(State.RESTARTING);
            Core.setTicks(5);
        }
    }

    /**
     * Listens for the kicking of a player.
     *
     * @param event The event.
     */
    @EventHandler
    public void onKick(PlayerKickEvent event) {
        if (event.getReason().equalsIgnoreCase("§c§lYou have lost! The game is restarting...")) {
            event.setCancelled(true);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("lobby");
            event.getPlayer().sendPluginMessage(Core.plugin, "BungeeCord", out.toByteArray());

            event.getPlayer().sendMessage("§8[§5NOVA§6U§8] §4You lost that game of §r" + Core.getPrefix() + "§4!");
        } else if (event.getReason().equalsIgnoreCase("§a§lYou have won! The game is restarting...")) {
            event.setCancelled(true);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("lobby");
            event.getPlayer().sendPluginMessage(Core.plugin, "BungeeCord", out.toByteArray());

            event.getPlayer().sendMessage("§8[§5NOVA§6U§8] §aYou won that game of §r" + Core.getPrefix() + "§a!");
        }
    }

    /**
     * Listens for an entity being damaged.
     *
     * @param event The event.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (Core.getState() != State.INGAME) return;

                if (event.getEntity() instanceof Player) {
                    final Player player = (Player) event.getEntity();

                    event.setDamage(0);
                    event.setCancelled(true);
                    player.teleport(Core.getLobbySpawnpoint());
                    player.sendMessage(Core.getPrefix() + "§4Not a good idea §e§l" + player.getName().toUpperCase() + "§4!");
                }
            }
        }
    }

    /**
     * Listens for player versus player combat.
     *
     * @param event The event.
     */
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (Core.getState() != State.INGAME) {
                event.setCancelled(true);
                return;
            } else {
                if (!Core.isPvpEnabled()) {
                    event.setCancelled(true);
                    damager.sendMessage(Core.getPrefix() + "§4This is not a PvP-oriented game!");
                    return;
                } else {
                    event.setCancelled(false);
                    Fight fight = new Fight(damager, damaged);

                    if (Core.isBloodEnabled()) {
                        damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                        damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                        damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                        damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                        damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                    }
                }
            }
        } else if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                if (Core.getState() != State.INGAME) {
                    event.setCancelled(true);
                    return;
                } else {
                    if (!Core.isArrowHitEnabled()) {
                        event.setCancelled(true);
                        shooter.sendMessage(Core.getPrefix() + "§4This is not a PvP-oriented game!");
                        return;
                    } else {
                        event.setCancelled(false);
                        Fight fight = new Fight(shooter, damaged);

                        if (Core.isBloodEnabled()) {
                            damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                            damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                            damaged.playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152);
                        }
                    }
                }
            }
        }
    }

    /**
     * Listens for breaking a block.
     *
     * @param event The event.
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (Core.getState() != State.INGAME) {
            event.setCancelled(true);
            return;
        } else {
            if (!Core.isBreakEnabled()) {
                event.setCancelled(true);
                player.sendMessage(Core.getPrefix() + "§4You cannot break blocks!");
                return;
            } else {
                event.setCancelled(false);
            }
        }
    }

    /**
     * Listens for placing blocks.
     *
     * @param event The event.
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (Core.getState() != State.INGAME) {
            event.setCancelled(true);
            return;
        } else {
            if (!Core.isBuildEnabled()) {
                event.setCancelled(true);
                player.sendMessage(Core.getPrefix() + "§4You cannot place blocks!");
                return;
            } else {
                event.setCancelled(false);
            }
        }
    }

    /**
     * Listens for a player chatting.
     *
     * @param event The event.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        if (player.isOp()) {
            event.setFormat("§8(§4Staff§8) §e§l" + player.getName().toUpperCase() + "§8: §a" + event.getMessage());
        } else {
            event.setFormat("§8(§1Player§8) §e§l" + player.getName().toUpperCase() + "§8: §a" + event.getMessage());
        }
    }

    /**
     * Listens for a player logging in.
     *
     * @param event The event.
     */
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        State currentState = Core.getState();
        switch (currentState) {
            case LOBBY:
                if (Bukkit.getOnlinePlayers().length >= Core.getMaxPlayers()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Too many players already! Try a different lobby!");
                    break;
                } else {
                    event.allow();
                    if (Core.getPlayers() == null) return;

                    Core.getPlayers().add(event.getPlayer().getName());
                    break;
                }
            case PREGAME:
                if (Bukkit.getOnlinePlayers().length >= Core.getMaxPlayers()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Too many players already! Try a different lobby!");
                    break;
                } else {
                    event.allow();
                    Core.getPlayers().add(event.getPlayer().getName());
                    break;
                }
            case WARMUP:
                if (Bukkit.getOnlinePlayers().length >= Core.getMaxPlayers()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Not enough slots for you to spectate! Try a different lobby!");
                    break;
                } else {
                    if (!Core.isSpectatingEnabled()) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Spectating is not allowed in this gamemode!");
                        break;
                    } else {
                        event.allow();
                        Core.getSpectators().add(event.getPlayer().getName());
                        break;
                    }
                }
            case INGAME:
                if (Bukkit.getOnlinePlayers().length >= Core.getMaxPlayers()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Not enough slots for you to spectate! Try a different lobby!");
                    break;
                } else {
                    if (!Core.isSpectatingEnabled()) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4Spectating is not allowed in this gamemode!");
                        return;
                    } else {
                        event.allow();
                        Core.getSpectators().add(event.getPlayer().getName());
                        break;
                    }
                }
            case RESTARTING:
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4The game is restarting!");
                break;
            default:
                System.out.println("This doesn't happen!"); // Impossible, enum constant has to be specified
                Bukkit.shutdown();
                break;
        }
    }

    /**
     * Listens for a player joining.
     *
     * @param event The event.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player p = event.getPlayer();

        if (Core.isDefaultScoreboardEnabled()) {
            Core.getOnline().setScore(Bukkit.getOnlinePlayers().length);
        }

        event.setJoinMessage(Core.getPrefix() + "§e§l" + event.getPlayer().getName().toUpperCase() + " §6has joined §8(§3" + Bukkit.getOnlinePlayers().length + "§8/§3" + Core.getMaxPlayers() + "§8)");

        event.getPlayer().teleport(Core.getLobbySpawnpoint());
        p.setHealth(20.0);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        event.getPlayer().getInventory().setItem(0, Core.getHub());

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (Core.getBoard() == null) return;

            players.setScoreboard(Core.getBoard());
        }
    }

    /**
     * Listens for quiting.
     *
     * @param event The event.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Core.isDefaultScoreboardEnabled()) {
            Core.getOnline().setScore(Bukkit.getOnlinePlayers().length - 1);
        }

        int playerAmount = Bukkit.getServer().getOnlinePlayers().length - 1;

        event.setQuitMessage(Core.getPrefix() + "§e§l" + event.getPlayer().getName().toUpperCase() + " §6left §8(§3" + playerAmount + "§8/§3" + Core.getMaxPlayers() + "§8)");

        if (Core.getState() == State.LOBBY && playerAmount < Core.getMinPlayers()) {
            ChatUtil.broadcast("§6Not enough players, the timer has been reset.");
            Core.setTicks(31);
        } else if (Core.getState() == State.PREGAME && playerAmount < Core.getMinPlayers()) {
            ChatUtil.broadcast("§6Not enough players, the timer has been reset.");
            Core.setState(State.LOBBY);
            Core.setTicks(31);
        } else if (Core.getState() == State.WARMUP && playerAmount < Core.getMinPlayers()) {
            ChatUtil.broadcast("§6Not enough players to start, the game has been put back into lobby mode.");

            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                players.teleport(Core.getLobbySpawnpoint());
                players.setHealth(20.0);
                players.setFoodLevel(20);
                players.setSaturation(8F);
                players.setRemainingAir(300);
                players.getInventory().clear();
                players.getInventory().setArmorContents(null);

                players.getInventory().setItem(0, Core.getHub());

                for (PotionEffect effects : players.getActivePotionEffects()) {
                    event.getPlayer().removePotionEffect(effects.getType());
                }
            }

            Core.setState(State.LOBBY);
            Core.setTicks(31);

            for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) {
                event.getPlayer().removePotionEffect(effect.getType());
            }

            event.getPlayer().setHealth(20.0);
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().teleport(Core.getLobbySpawnpoint());
            event.getPlayer().getInventory().clear();
            event.getPlayer().getInventory().setArmorContents(null);

            event.getPlayer().getInventory().setItem(0, Core.getHub());
        }
        if (Core.getState() == State.INGAME && playerAmount < 1) {
            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                players.kickPlayer("§2You have won the game");
            }

            Core.setTicks(5);
            Core.setState(State.RESTARTING);
        }
    }

    /**
     * Listens for dropping.
     *
     * @param event The event.
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (Core.getState() != State.INGAME) {
            event.setCancelled(true);
            return;
        } else {
            if (!Core.isDropItemsEnabled()) {
                event.setCancelled(true);
                player.sendMessage(Core.getPrefix() + "§4You cannot drop items!");
                return;
            } else {
                event.setCancelled(false);
            }
        }
    }

    /**
     * Listens for moving.
     *
     * @param event The event.
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Hub.alreadySending.contains(event.getPlayer().getName())) {
            if (event.getTo().getY() == event.getFrom().getY() || event.getTo().getX() == event.getFrom().getX() || event.getTo().getZ() == event.getFrom().getZ())
                return;

            event.setTo(event.getFrom());
        }

        if (Core.getState() == State.WARMUP) {
            if (event.getFrom().getPitch() != event.getTo().getPitch() || event.getFrom().getYaw() != event.getTo().getYaw())
                return;

            event.setTo(event.getFrom());
        }
    }

    /**
     * Listens for picking up items.
     *
     * @param event The event.
     */
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (Core.getState() != State.INGAME) {
            event.setCancelled(true);
            return;
        } else {
            if (!Core.isPickupItemsEnabled()) {
                event.setCancelled(true);
                return;
            } else {
                event.setCancelled(false);
            }
        }
    }
}

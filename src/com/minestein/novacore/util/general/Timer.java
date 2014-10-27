package com.minestein.novacore.util.general;

import com.minestein.novacore.Core;
import com.minestein.novacore.event.StateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Timer {

    /**
     * Runs the game timer.
     */
    public static void runTimer() {
        if (Core.getState() == State.LOBBY && Bukkit.getServer().getOnlinePlayers().length >= Core.getMinPlayers()) {
            if (Core.getTicks() <= 31) {
                Core.setTicks(Core.getTicks() - 1);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setLevel(Core.getTicks());
                }

                if (Core.isDefaultScoreboardEnabled()) {
                    Core.getTimeLeft().setScore(Core.getTicks());
                }

                if (Core.getTicks() == 60 ||
                        Core.getTicks() == 45 ||
                        Core.getTicks() == 30 ||
                        Core.getTicks() == 15 ||
                        Core.getTicks() == 10) {
                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds left until voting ends!");
                } else if (Core.getTicks() == 5 ||
                        Core.getTicks() == 4 ||
                        Core.getTicks() == 3 ||
                        Core.getTicks() == 2 ||
                        Core.getTicks() == 1) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.CLICK, 1F, 1F);
                    }

                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds left until voting ends!");
                } else if (Core.getTicks() == 0) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.getInventory().clear();
                    }

                    ChatUtil.broadcast("§6Voting is over.");
                    Core.setState(State.PREGAME);
                    Core.setTicks(11);
                }
            }
        } else if (Core.getState() == State.PREGAME) {
            if (Core.getTicks() <= 11) {
                Core.setTicks(Core.getTicks() - 1);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setLevel(Core.getTicks());
                }

                if (Core.isDefaultScoreboardEnabled()) {
                    Core.getTimeLeft().setScore(Core.getTicks());
                }

                if (Core.getTicks() == 10) {
                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds left until teleporting!");
                } else if (Core.getTicks() == 5 ||
                        Core.getTicks() == 4 ||
                        Core.getTicks() == 3 ||
                        Core.getTicks() == 2 ||
                        Core.getTicks() == 1) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.CLICK, 1F, 1F);
                    }

                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds left until teleporting!");
                } else if (Core.getTicks() == 0) {
                    ChatUtil.broadcast("§6Teleporting...");

                    Core.setState(State.WARMUP);
                    Core.setTicks(11);
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.setLevel(Core.getTicks());
                    }

                    Bukkit.getScheduler().runTaskLater(Core.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                                for (int i = 0; i < 2; i++) {
                                    players.sendMessage("");
                                }
                                players.sendMessage(Core.ingameMessageHeader);
                                players.sendMessage(Core.getIngameMessage());
                                for (int i = 0; i < 2; i++) {
                                    players.sendMessage("");
                                }
                            }
                        }
                    }, 100);

                    int counter = 0;

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.EXPLODE, 1F, 1F);
                        players.playSound(players.getLocation(), Sound.LEVEL_UP, 1F, 1F);

                        players.teleport(Core.getIngamePoints()[counter]);
                        counter++;
                    }
                }
            }
        } else if (Core.getState() == State.WARMUP) {
            if (Core.getTicks() <= 11) {
                Core.setTicks(Core.getTicks() - 1);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setLevel(Core.getTicks());
                }

                if (Core.isDefaultScoreboardEnabled()) {
                    Core.getTimeLeft().setScore(Core.getTicks());
                }

                if (Core.getTicks() == 10) {
                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds until the game begins!");
                } else if (Core.getTicks() == 5 ||
                        Core.getTicks() == 4 ||
                        Core.getTicks() == 3 ||
                        Core.getTicks() == 2 ||
                        Core.getTicks() == 1) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.CLICK, 1F, 1F);
                    }

                    ChatUtil.broadcast("§e" + Core.getTicks() + " §6seconds until the game begins!");
                } else if (Core.getTicks() == 0) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.LEVEL_UP, 1F, 1F);

                        if (Core.isCustomLoadoutEnabled()) {
                            players.getInventory().setArmorContents(Core.getCurrentLoadout().getArmor());
                            players.getInventory().setArmorContents(Core.getCurrentLoadout().getInventory());

                            if (Core.isAnnounceLoadoutEnabled()) {
                                players.sendMessage(Core.getPrefix() + "§6You have received your loadout!");
                            }
                        }
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.playSound(players.getLocation(), Sound.EXPLODE, 1F, 1F);
                    }

                    ChatUtil.broadcast("§6The game has begun!");

                    Core.setState(State.INGAME);
                    Core.setTicks(1000);
                }
            }
        } else if (Core.getState() == State.RESTARTING) {
            if (Core.getTicks() <= 5) {
                Core.setTicks(Core.getTicks() - 1);

                if (Core.getTicks() == 3) {
                    for (Block blocks : Core.getBlocks()) {

                    }
                } else if (Core.getTicks() == 0) {
                    Core.setState(State.LOBBY);
                    Core.setTicks(31);
                }
            }
        }
    }
}

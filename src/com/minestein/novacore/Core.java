package com.minestein.novacore;

import com.minestein.novacore.command.Guide;
import com.minestein.novacore.command.Hub;
import com.minestein.novacore.command.Join;
import com.minestein.novacore.command.Youtube;
import com.minestein.novacore.listener.Events;
import com.minestein.novacore.util.general.Fight;
import com.minestein.novacore.util.general.Loadout;
import com.minestein.novacore.util.general.State;
import com.minestein.novacore.util.general.Timer;
import me.confuser.barapi.BarAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Random;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class Core extends JavaPlugin {

    /* TODO
     * - scoreboard under names
     */

    private static int ticks;
    private static String prefix = "§8[§5Game§8] §f";
    private static String softPrefix = "Game";
    private static String currentMessage;
    public static final String ingameMessageHeader = "§c§l/ §4§l/ §c§l/ §4§l/ §c§l/ §5§lNova§6§lUniverse §c§l/ §4§l/ §c§l/ §4§l/ §c§l/";
    private static String guide = "§bExample Guide";
    private static final int minPlayers = 2;
    private static int maxPlayers = 12;
    private static State state;
    public static Core plugin;
    private static Location lobbySpawnpoint;
    private static Location[] ingamePoints;
    private static String[] ingameMessage;
    private static String[] announcements;
    public static final String[] validColorCodes = new String[]{
            "4", "c", "e", "a", "b", "9", "d"
    };
    private static boolean pvpEnabled;
    private static boolean buildEnabled;
    private static boolean dropItemsEnabled;
    private static boolean pickupItemsEnabled;
    private static boolean breakEnabled;
    private static boolean arrowHitEnabled;
    private static boolean clearItemsEnabled;
    private static boolean spectatingEnabled;
    private static boolean fillBucketEnabled;
    private static boolean placeBucketEnabled;
    private static boolean defaultScoreboardEnabled;
    private static boolean customLoadoutEnabled;
    private static boolean announceLoadoutEnabled;
    private static boolean bloodEnabled;
    private static boolean votingEnabled;
    private static Scoreboard board;
    private static Objective boardObjective;
    private static Score online;
    private static Score version;
    private static Score timeLeft;
    private static ArrayList<Block> blocks;
    private static ArrayList<String> spectators;
    private static ArrayList<String> players;
    private static ItemStack hub;
    public static Random random;
    private static Loadout currentLoadout;

    /**
     * Takes the guides string and creates a book.
     *
     * @return The new book.
     */
    public static ItemStack getGuideItem() {
        ItemStack i = new ItemStack(Material.WRITTEN_BOOK);
        {
            BookMeta m = (BookMeta) i.getItemMeta();
            m.setAuthor("NovaUniverse");
            m.addPage("1");
            m.setPage(1, getGuide());
            m.setDisplayName("");
            ArrayList<String> l = new ArrayList<>();
            l.add("§5§oA guide used");
            l.add("§5§ofor assistance in");
            l.add("§5§o" + Core.getSoftPrefix());
            m.setLore(l);
            i.setItemMeta(m);
        }

        return i;
    }

    /**
     * Gets the current guide for the game.
     *
     * @return The guide.
     */
    public static String getGuide() {
        return guide;
    }

    /**
     * Sets the current guide for the game.
     *
     * @param guide The new guide.
     */
    public static void setGuide(String guide) {
        String newGuide = "§5§oWelcome to "+getSoftPrefix()+"\n\n§7"+guide;

        Core.guide = newGuide;
    }

    /**
     * Gets the current message in the boss bar.
     *
     * @return The boss bar message.
     */
    public static String getCurrentMessage() {
        return currentMessage;
    }

    /**
     * Sets the current message of the boss bar.
     *
     * @param currentMessage The new message.
     */
    public static void setCurrentMessage(String currentMessage) {
        Core.currentMessage = currentMessage;
    }

    /**
     * Gets the plugin instance.
     *
     * @return The plugin.
     */
    public static Core getPlugin() {
        return plugin;
    }

    /**
     * Gets the valid color codes for the games.
     *
     * @return The valid color codes.
     */
    public String[] getValidColorCodes() {
        return validColorCodes;
    }

    /**
     * Gets the in-game message header.
     *
     * @return The message header.
     */
    public static String getIngameMessageHeader() {
        return ingameMessageHeader;
    }

    /**
     * Gets whether or not voting is enabled.
     *
     * @return Voting enabled boolean.
     */
    public static boolean isVotingEnabled() {
        return votingEnabled;
    }

    /**
     * Sets whether or not voting is enabled.
     *
     * @param votingEnabled The new boolean.
     */
    public static void setVotingEnabled(boolean votingEnabled) {
        Core.votingEnabled = votingEnabled;
    }

    /**
     * Gets the game's random number generator.
     *
     * @return The random.
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * Gets whether or not blood is enabled.
     *
     * @return Blood enabled boolean.
     */
    public static boolean isBloodEnabled() {
        return bloodEnabled;
    }

    /**
     * Sets whether or not blood is enabled.
     *
     * @param bloodEnabled The new boolean.
     */
    public static void setBloodEnabled(boolean bloodEnabled) {
        Core.bloodEnabled = bloodEnabled;
    }

    /**
     * Gets the soft prefix used in bar messages and the scoreboard objective.
     *
     * @return The soft prefix.
     */
    public static String getSoftPrefix() {
        return softPrefix;
    }

    /**
     * Sets the soft prefix used in bar messages and the scoreboard objective.
     *
     * @param softPrefix The new soft prefix.
     */
    public static void setSoftPrefix(String softPrefix) {
        Core.softPrefix = softPrefix;
    }

    /**
     * @return
     */
    public static boolean isAnnounceLoadoutEnabled() {
        return announceLoadoutEnabled;
    }

    /**
     * @param announceLoadoutEnabled
     */
    public static void setAnnounceLoadoutEnabled(boolean announceLoadoutEnabled) {
        Core.announceLoadoutEnabled = announceLoadoutEnabled;
    }

    /**
     * @return
     */
    public static Loadout getCurrentLoadout() {
        return currentLoadout;
    }

    /**
     * @param currentLoadout
     */
    public static void setCurrentLoadout(Loadout currentLoadout) {
        Core.currentLoadout = currentLoadout;
    }

    /**
     * @return
     */
    public static boolean isCustomLoadoutEnabled() {
        return customLoadoutEnabled;
    }

    /**
     * @param customLoadoutEnabled
     */
    public static void setCustomLoadoutEnabled(boolean customLoadoutEnabled) {
        Core.customLoadoutEnabled = customLoadoutEnabled;
    }

    /**
     * @return
     */
    public static boolean isFillBucketEnabled() {
        return fillBucketEnabled;
    }

    /**
     * @param fillBucketEnabled
     */
    public static void setFillBucketEnabled(boolean fillBucketEnabled) {
        Core.fillBucketEnabled = fillBucketEnabled;
    }

    /**
     * @return
     */
    public static boolean isPlaceBucketEnabled() {
        return placeBucketEnabled;
    }

    /**
     * @param placeBucketEnabled
     */
    public static void setPlaceBucketEnabled(boolean placeBucketEnabled) {
        Core.placeBucketEnabled = placeBucketEnabled;
    }

    /**
     * @return
     */
    public static boolean isDefaultScoreboardEnabled() {
        return defaultScoreboardEnabled;
    }

    /**
     * @param defaultScoreboardEnabled
     */
    public static void setDefaultScoreboardEnabled(boolean defaultScoreboardEnabled) {
        Core.defaultScoreboardEnabled = defaultScoreboardEnabled;
    }


    /**
     * @return
     */
    public static ArrayList<String> getSpectators() {
        return spectators;
    }

    /**
     * @return
     */
    public static ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * @return
     */
    public static boolean isSpectatingEnabled() {
        return spectatingEnabled;
    }

    /**
     * @param spectatingEnabled
     */
    public static void setSpectatingEnabled(boolean spectatingEnabled) {
        Core.spectatingEnabled = spectatingEnabled;
    }

    /**
     * @return
     */
    public static Score getTimeLeft() {
        return timeLeft;
    }

    /**
     * @return
     */
    public static boolean isClearItemsEnabled() {
        return clearItemsEnabled;
    }

    /**
     * @param clearItemsEnabled
     */
    public static void setClearItemsEnabled(boolean clearItemsEnabled) {
        Core.clearItemsEnabled = clearItemsEnabled;
    }

    /**
     * @return
     */
    public static ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * @return
     */
    public static boolean isArrowHitEnabled() {
        return arrowHitEnabled;
    }

    /**
     * @param arrowHitEnabled
     */
    public static void setArrowHitEnabled(boolean arrowHitEnabled) {
        Core.arrowHitEnabled = arrowHitEnabled;
    }

    /**
     * @return
     */
    public static Scoreboard getBoard() {
        return board;
    }

    /**
     * @param board
     */
    public static void setBoard(Scoreboard board) {
        Core.board = board;
    }

    /**
     * @return
     */
    public static Objective getBoardObjective() {
        return boardObjective;
    }

    /**
     * @param boardObjective
     */
    public static void setBoardObjective(Objective boardObjective) {
        Core.boardObjective = boardObjective;
    }

    /**
     * @return
     */
    public static Score getOnline() {
        return online;
    }

    /**
     * @param online
     */
    public static void setOnline(Score online) {
        Core.online = online;
    }

    /**
     * @return
     */
    public static Score getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public static void setVersion(Score version) {
        Core.version = version;
    }

    /**
     * @return
     */
    public static boolean isBreakEnabled() {
        return breakEnabled;
    }

    /**
     * @param breakEnabled
     */
    public static void setBreakEnabled(boolean breakEnabled) {
        Core.breakEnabled = breakEnabled;
    }

    /**
     * @return
     */
    public static boolean isPickupItemsEnabled() {
        return pickupItemsEnabled;
    }

    /**
     * @param pickupItemsEnabled
     */
    public static void setPickupItemsEnabled(boolean pickupItemsEnabled) {
        Core.pickupItemsEnabled = pickupItemsEnabled;
    }

    /**
     * @return
     */
    public static boolean isDropItemsEnabled() {
        return dropItemsEnabled;
    }

    /**
     * @param dropItemsEnabled
     */
    public static void setDropItemsEnabled(boolean dropItemsEnabled) {
        Core.dropItemsEnabled = dropItemsEnabled;
    }

    /**
     * @return
     */
    public static Location[] getIngamePoints() {
        return ingamePoints;
    }

    /**
     * @return
     */
    public static Location getLobbySpawnpoint() {
        return lobbySpawnpoint;
    }

    /**
     * @param lobbySpawnpoint
     */
    public static void setLobbySpawnpoint(Location lobbySpawnpoint) {
        Core.lobbySpawnpoint = lobbySpawnpoint;
    }

    /**
     * @return
     */
    public static int getTicks() {
        return ticks;
    }

    /**
     * @param ticks
     */
    public static void setTicks(int ticks) {
        Core.ticks = ticks;
    }

    /**
     * @return
     */
    public static boolean isPvpEnabled() {
        return pvpEnabled;
    }

    /**
     * @param pvpEnabled
     */
    public static void setPvpEnabled(boolean pvpEnabled) {
        Core.pvpEnabled = pvpEnabled;
    }

    /**
     * @return
     */
    public static boolean isBuildEnabled() {
        return buildEnabled;
    }

    /**
     * @param buildEnabled
     */
    public static void setBuildEnabled(boolean buildEnabled) {
        Core.buildEnabled = buildEnabled;
    }

    /**
     * @return
     */
    public static String getPrefix() {
        return prefix;
    }

    /**
     * @param name
     */
    public static void setPrefix(String name) {
        String prefix = "§8[§5" + name + "§8] §f";

        Core.prefix = prefix;
    }

    /**
     * @return
     */
    public static int getMinPlayers() {
        return minPlayers;
    }

    /**
     * @return
     */
    public static int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @param maxPlayers
     */
    public static void setMaxPlayers(int maxPlayers) {
        Core.maxPlayers = maxPlayers;
    }

    /**
     * @return
     */
    public static State getState() {
        return state;
    }

    /**
     * @param state
     */
    public static void setState(State state) {
        Core.state = state;
    }

    /**
     * @return
     */
    public static ItemStack getHub() {
        return hub;
    }

    /**
     * @return
     */
    public static String[] getIngameMessage() {
        return ingameMessage;
    }

    /**
     * @param ingameMessage
     */
    public static void setIngameMessage(String[] ingameMessage) {
        Core.ingameMessage = ingameMessage;
    }

    /**
     * @param ingamePoints
     */
    public static void setIngamePoints(Location[] ingamePoints) {
        Core.ingamePoints = ingamePoints;
    }

    /**
     * @param timeLeft
     */
    public static void setTimeLeft(Score timeLeft) {
        Core.timeLeft = timeLeft;
    }

    /**
     * @param blocks
     */
    public static void setBlocks(ArrayList<Block> blocks) {
        Core.blocks = blocks;
    }

    /**
     * @param spectators
     */
    public static void setSpectators(ArrayList<String> spectators) {
        Core.spectators = spectators;
    }

    /**
     * @param players
     */
    public static void setPlayers(ArrayList<String> players) {
        Core.players = players;
    }

    /**
     * @param hub
     */
    public static void setHub(ItemStack hub) {
        Core.hub = hub;
    }

    /**
     * @return
     */
    public static String[] getAnnouncements() {
        return announcements;
    }

    /**
     * @param announcements
     */
    public static void setAnnouncements(String[] announcements) {
        Core.announcements = announcements;
    }

    public static void refreshScoreboard() {
        Scoreboard newScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective newObjective = newScoreboard.registerNewObjective("newScoreboard", "dummy");
        newObjective.setDisplayName("§" + validColorCodes[random.nextInt(validColorCodes.length)] + "§l" + getSoftPrefix());
        newObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score newOnline = newObjective.getScore("§eOnline");
        newOnline.setScore(Bukkit.getOnlinePlayers().length);
        Score newTimeLeft = newObjective.getScore("§eTime");
        newTimeLeft.setScore(Core.getTicks());

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setScoreboard(newScoreboard);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem().getType() != Material.BONE) return;

        final Wolf wolf = p.getWorld().spawn(p.getLocation(), Wolf.class);

        Bukkit.getScheduler().runTaskLater(this, wolf::remove, 80);
    }

    @Override
    public void onEnable() {
        plugin = this;
        random = new Random();

        hub = new ItemStack(Material.BLAZE_POWDER);
        {
            ItemMeta m = hub.getItemMeta();
            m.setDisplayName("§c§lGo to Hub §7§o(Right-Click)");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Be sent back to hub!");
            m.setLore(lore);
            hub.setItemMeta(m);
        }

        getCommand("youtube").setExecutor(new Youtube());
        getCommand("hub").setExecutor(new Hub());
        getCommand("join").setExecutor(new Join());
        getCommand("guide").setExecutor(new Guide());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        blocks = new ArrayList<>();

        Fight.fights.clear();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        setState(State.LOBBY);
        setTicks(31);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setHealth(20.0);
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                BarAPI.setMessage(announcements[random.nextInt(announcements.length)]);
            }
        }, 0, 40);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                currentMessage = announcements[random.nextInt(announcements.length)];
            }
        }, 0, 40);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                BarAPI.setMessage("§" + validColorCodes[random.nextInt(validColorCodes.length)] + "§l" + getSoftPrefix() + " §e" + currentMessage);
            }
        }, 5, 10);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (World worlds : Bukkit.getWorlds()) {
                    for (Chunk chunks : worlds.getLoadedChunks()) {
                        for (Entity entities : chunks.getEntities()) {
                            if (!(entities instanceof Player && !(entities instanceof Pig) && !(entities instanceof EnderPearl) && !(entities instanceof Snowball))) {
                                if (entities instanceof Item) {
                                    if (isClearItemsEnabled()) {
                                        entities.remove();
                                        return;
                                    } else {
                                        return;
                                    }
                                } else {
                                    entities.remove();
                                }
                            }
                        }
                    }
                }

                if (isDefaultScoreboardEnabled()) {
                     /*board = Bukkit.getScoreboardManager().getNewScoreboard();
                 boardObjective = board.registerNewObjective("mainBoard", "dummy");
                   boardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
                   boardObjective.setDisplayName("§"+validColorCodes[getRandom().nextInt(validColorCodes.length)]+"§l"+getSoftPrefix());
                   online = boardObjective.getScore("§4Online");
                    version = boardObjective.getScore("§cVersion");
                    version.setScore(1);
                    timeLeft = boardObjective.getScore("§eTime");
                    timeLeft.setScore(31);
                    */

                    refreshScoreboard();
                }

                Timer.runTimer();
            }
        }, 0, 20);

        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
    }
}

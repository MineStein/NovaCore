package com.minestein.novacore;

import com.minestein.novacore.command.Hub;
import com.minestein.novacore.command.TeamJoin;
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
import org.bukkit.inventory.ItemStack;
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

    private static int ticks;
    private static String prefix = "§8[§5Game§8] §f";
    private static String softPrefix = "Game";
    private static String currentMessage;
    private static final int minPlayers = 2;
    private static int maxPlayers = 12;
    private static State state;
    public static Core plugin;
    private static Location lobbySpawnpoint;
    private static Location[] ingamePoints;
    private static String[] ingameMessage;
    private static String[] announcements;
    final String[] validColorCodes = new String[]{
            "4", "c", "e", "a", "b", "9", "d"
    };
    public static final String ingameMessageHeader = "§c§l/ §4§l/ §c§l/ §4§l/ §c§l/ §5§lNova§6§lUniverse §c§l/ §4§l/ §c§l/ §4§l/ §c§l/";
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

    public static String getCurrentMessage() {
        return currentMessage;
    }

    public static void setCurrentMessage(String currentMessage) {
        Core.currentMessage = currentMessage;
    }

    public static Core getPlugin() {
        return plugin;
    }

    public static void setPlugin(Core plugin) {
        Core.plugin = plugin;
    }

    public String[] getValidColorCodes() {
        return validColorCodes;
    }

    public static String getIngameMessageHeader() {
        return ingameMessageHeader;
    }

    public static boolean isVotingEnabled() {
        return votingEnabled;
    }

    public static void setVotingEnabled(boolean votingEnabled) {
        Core.votingEnabled = votingEnabled;
    }

    public static Random getRandom() {
        return random;
    }

    public static void setRandom(Random random) {
        Core.random = random;
    }

    public int getChangeText() {
        return changeText;
    }

    public void setChangeText(int changeText) {
        this.changeText = changeText;
    }

    public static boolean isBloodEnabled() {
        return bloodEnabled;
    }

    public static void setBloodEnabled(boolean bloodEnabled) {
        Core.bloodEnabled = bloodEnabled;
    }

    public static String getSoftPrefix() {
        return softPrefix;
    }

    public static void setSoftPrefix(String softPrefix) {
        Core.softPrefix = softPrefix;
    }

    public static boolean isAnnounceLoadoutEnabled() {
        return announceLoadoutEnabled;
    }

    public static void setAnnounceLoadoutEnabled(boolean announceLoadoutEnabled) {
        Core.announceLoadoutEnabled = announceLoadoutEnabled;
    }

    public static Loadout getCurrentLoadout() {
        return currentLoadout;
    }

    public static void setCurrentLoadout(Loadout currentLoadout) {
        Core.currentLoadout = currentLoadout;
    }

    public static boolean isCustomLoadoutEnabled() {
        return customLoadoutEnabled;
    }

    public static void setCustomLoadoutEnabled(boolean customLoadoutEnabled) {
        Core.customLoadoutEnabled = customLoadoutEnabled;
    }

    public static boolean isFillBucketEnabled() {
        return fillBucketEnabled;
    }

    public static void setFillBucketEnabled(boolean fillBucketEnabled) {
        Core.fillBucketEnabled = fillBucketEnabled;
    }

    public static boolean isPlaceBucketEnabled() {
        return placeBucketEnabled;
    }

    public static void setPlaceBucketEnabled(boolean placeBucketEnabled) {
        Core.placeBucketEnabled = placeBucketEnabled;
    }

    public static boolean isDefaultScoreboardEnabled() {
        return defaultScoreboardEnabled;
    }

    public static void setDefaultScoreboardEnabled(boolean defaultScoreboardEnabled) {
        Core.defaultScoreboardEnabled = defaultScoreboardEnabled;
    }


    public static ArrayList<String> getSpectators() {
        return spectators;
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static boolean isSpectatingEnabled() {
        return spectatingEnabled;
    }

    public static void setSpectatingEnabled(boolean spectatingEnabled) {
        Core.spectatingEnabled = spectatingEnabled;
    }

    public static Score getTimeLeft() {
        return timeLeft;
    }

    public static boolean isClearItemsEnabled() {
        return clearItemsEnabled;
    }

    public static void setClearItemsEnabled(boolean clearItemsEnabled) {
        Core.clearItemsEnabled = clearItemsEnabled;
    }

    public static ArrayList<Block> getBlocks() {
        return blocks;
    }

    public static boolean isArrowHitEnabled() {
        return arrowHitEnabled;
    }

    public static void setArrowHitEnabled(boolean arrowHitEnabled) {
        Core.arrowHitEnabled = arrowHitEnabled;
    }

    public static Scoreboard getBoard() {
        return board;
    }

    public static void setBoard(Scoreboard board) {
        Core.board = board;
    }

    public static Objective getBoardObjective() {
        return boardObjective;
    }

    public static void setBoardObjective(Objective boardObjective) {
        Core.boardObjective = boardObjective;
    }

    public static Score getOnline() {
        return online;
    }

    public static void setOnline(Score online) {
        Core.online = online;
    }

    public static Score getVersion() {
        return version;
    }

    public static void setVersion(Score version) {
        Core.version = version;
    }

    public static boolean isBreakEnabled() {
        return breakEnabled;
    }

    public static void setBreakEnabled(boolean breakEnabled) {
        Core.breakEnabled = breakEnabled;
    }

    public static boolean isPickupItemsEnabled() {
        return pickupItemsEnabled;
    }

    public static void setPickupItemsEnabled(boolean pickupItemsEnabled) {
        Core.pickupItemsEnabled = pickupItemsEnabled;
    }

    public static boolean isDropItemsEnabled() {
        return dropItemsEnabled;
    }

    public static void setDropItemsEnabled(boolean dropItemsEnabled) {
        Core.dropItemsEnabled = dropItemsEnabled;
    }

    public static Location[] getIngamePoints() {
        return ingamePoints;
    }

    public static Location getLobbySpawnpoint() {
        return lobbySpawnpoint;
    }

    public static void setLobbySpawnpoint(Location lobbySpawnpoint) {
        Core.lobbySpawnpoint = lobbySpawnpoint;
    }

    public static int getTicks() {
        return ticks;
    }

    public static void setTicks(int ticks) {
        Core.ticks = ticks;
    }

    public static boolean isPvpEnabled() {
        return pvpEnabled;
    }

    public static void setPvpEnabled(boolean pvpEnabled) {
        Core.pvpEnabled = pvpEnabled;
    }

    public static boolean isBuildEnabled() {
        return buildEnabled;
    }

    public static void setBuildEnabled(boolean buildEnabled) {
        Core.buildEnabled = buildEnabled;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String name) {
        String prefix = "§8[§5" + name + "§8] §f";

        Core.prefix = prefix;
    }

    public static int getMinPlayers() {
        return minPlayers;
    }

    public static int getMaxPlayers() {
        return maxPlayers;
    }

    public static void setMaxPlayers(int maxPlayers) {
        Core.maxPlayers = maxPlayers;
    }

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        Core.state = state;
    }

    public static ItemStack getHub() {
        return hub;
    }

    public static String[] getIngameMessage() {
        return ingameMessage;
    }

    public static void setIngameMessage(String[] ingameMessage) {
        Core.ingameMessage = ingameMessage;
    }

    public static void setIngamePoints(Location[] ingamePoints) {
        Core.ingamePoints = ingamePoints;
    }

    public static void setTimeLeft(Score timeLeft) {
        Core.timeLeft = timeLeft;
    }

    public static void setBlocks(ArrayList<Block> blocks) {
        Core.blocks = blocks;
    }

    public static void setSpectators(ArrayList<String> spectators) {
        Core.spectators = spectators;
    }

    public static void setPlayers(ArrayList<String> players) {
        Core.players = players;
    }

    public static void setHub(ItemStack hub) {
        Core.hub = hub;
    }

    public static String[] getAnnouncements() {
        return announcements;
    }

    public static void setAnnouncements(String[] announcements) {
        Core.announcements = announcements;
    }

    int changeText = 0;

    public void changeText(final Objective o, long time, final String... text) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                if (changeText >= text.length)
                    changeText = 0;
                o.setDisplayName(text[changeText]);
                changeText++;
            }
        }
                , 0, time);
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
        getCommand("join").setExecutor(new TeamJoin());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        if (isDefaultScoreboardEnabled()) {
            board = Bukkit.getScoreboardManager().getNewScoreboard();
            boardObjective = board.registerNewObjective("mainBoard", "dummy");
            boardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
            boardObjective.setDisplayName(getSoftPrefix());
            online = boardObjective.getScore("§4Online");
            version = boardObjective.getScore("§cVersion");
            version.setScore(1);
            timeLeft = boardObjective.getScore("§eTime");
            timeLeft.setScore(31);

            changeText(boardObjective, 1000, "§" + validColorCodes[random.nextInt(validColorCodes.length)] + "§l" + getSoftPrefix());
        }

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

                Timer.runTimer();
            }
        }, 0, 20);

        Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
    }
}

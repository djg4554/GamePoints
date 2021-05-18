package org.spigotparty.dev.free.gamepoints;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotparty.dev.free.gamepoints.commands.GPCommand;
import org.spigotparty.dev.free.gamepoints.handlers.AccountHandler;
import org.spigotparty.dev.free.gamepoints.handlers.FileHandler;
import org.spigotparty.dev.free.gamepoints.listener.PlayerJoinEventListener;
import org.spigotparty.dev.free.gamepoints.listener.PlayerQuitListener;

import java.util.ArrayList;


public final class GamePoints extends JavaPlugin {

    private AccountHandler accountHandler;
    public final double basePoints;
    private ArrayList<PlayerAccount> onlinePlayers;
    private GamePointsAPI gamePointsAPI;
    private FileHandler playersFileHandler;

    public GamePoints() {
        saveDefaultConfig();

        basePoints = getConfig().getDouble("economy.base-funds");


    }


    @Override
    public void onEnable() {
        // Plugin startup logic

        //Check which database use and in case set up the right one: Priority sqlite
        onlinePlayers = new ArrayList<>();



        //setup Handlers
        playersFileHandler = new FileHandler(this, "data.yml");
        playersFileHandler.saveDefaultConfig();
        accountHandler = new AccountHandler(this);
        accountHandler.loadAllPlayer();


        //Register Events
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this, accountHandler), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, accountHandler), this);

        //registerCommands
        getCommand("gp").setExecutor(new GPCommand(this));
        hookPlaceholder();

        new BukkitRunnable() {
            @Override
            public void run() {
                accountHandler.saveAllPlayers();
                accountHandler.loadAllPlayer();
            }
        }.runTaskTimer(this, 5* 60 * 20,5* 60 * 20);


    }


    private void hookPlaceholder() {

        gamePointsAPI = new GamePointsAPI(this);
        gamePointsAPI.register();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        accountHandler.saveAllPlayers();
    }


    public AccountHandler getAccountManager() {
        return accountHandler;
    }

    public ArrayList<PlayerAccount> getOnlinePlayers() {
        return onlinePlayers;
    }

    public FileHandler getPlayersFile() {
        return playersFileHandler;
    }
}

package org.spigotparty.dev.free.gamepoints.handlers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.spigotparty.dev.free.gamepoints.GamePoints;
import org.spigotparty.dev.free.gamepoints.PlayerAccount;

public class AccountHandler {
    private final GamePoints plugin;


    public AccountHandler(GamePoints plugin) {
        this.plugin = plugin;
    }

    /**
     * Check for the existance of the player and create an account for him
     * if he doesnt alredy has one, and load it
     * @param player
     */
    public void loadPlayer(Player player) {
        if (!exists(player)) {
            createAccount(player);
        }
        loadAccount(player);
    }

    public void loadAllPlayer() {
        if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                loadPlayer(player);
            }
        }

    }

    public boolean hasAccount(OfflinePlayer player) {
        return exists(player);
    }

    public PlayerAccount getOnlineAccount(Player player) {

        for (PlayerAccount playerAccount : plugin.getOnlinePlayers()) {
            if (playerAccount.getHolder().getUniqueId().equals(player.getUniqueId())) {
                return playerAccount;
            }
        }
        return null;
    }

    public PlayerAccount getAccount(OfflinePlayer player) {
        if (player.isOnline()) {
            return getOnlineAccount((Player) player);
        } else {
            return new PlayerAccount(player, plugin.getPlayersFile().getConfig().getDouble("players."+player.getUniqueId()));
        }
    }

    public void saveAccount(PlayerAccount playerAccount) {
        plugin.getPlayersFile().getConfig().set("players."+playerAccount.getHolder().getUniqueId(), playerAccount.getAmount());
        plugin.getPlayersFile().saveConfig();
    }

    public void saveAllPlayers() {
        unloadAllAccount();
    }

    /**
     *
     * @param player
     * @return true if the player is present in the database, false if it doesnt
     */
    private boolean exists(OfflinePlayer player) {

        return plugin.getPlayersFile().getConfig().getDouble("players."+player.getUniqueId(), Double.NaN) != Double.NaN ;
    }

    private boolean isLoaded(Player player) {
        for (PlayerAccount playerAccount : plugin.getOnlinePlayers()) {
            if (playerAccount.getHolder().getUniqueId().equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }
    /**=
     * Create an account for the player
     * the player doesnt have an account
     *
     * @param player the player whose account is going to be created
     */
    private void createAccount(OfflinePlayer player) {
        plugin.getPlayersFile().getConfig().set("players."+player.getUniqueId(), plugin.basePoints);
    }

    /**
     * Load the playerAccount in the OnlinePlayers List
     *
     * @param player The player that will be loaded
     *
     */
    private void loadAccount(@NotNull OfflinePlayer player) {
        plugin.getOnlinePlayers().add(new PlayerAccount(player, plugin.getPlayersFile().getConfig().getDouble("players."+player.getUniqueId())));
    }



    /**
     * Save all the payer online
     */
    private void unloadAllAccount() {
        for (PlayerAccount playerAccount : plugin.getOnlinePlayers()) {
            this.saveAccount(playerAccount);
        }
        plugin.getOnlinePlayers().clear();
    }





}

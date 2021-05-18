package org.spigotparty.dev.free.gamepoints.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotparty.dev.free.gamepoints.GamePoints;
import org.spigotparty.dev.free.gamepoints.PlayerAccount;
import org.spigotparty.dev.free.gamepoints.handlers.AccountHandler;

public class PlayerQuitListener extends SPListener implements Listener {

    public PlayerQuitListener(GamePoints gamePoints, AccountHandler accountHandler) {
        super(gamePoints, accountHandler);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerAccount playerAccount = accountHandler.getOnlineAccount(player);
        if ( playerAccount != null) {
            accountHandler.saveAccount(playerAccount);
        }
    }
}

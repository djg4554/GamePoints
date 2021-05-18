package org.spigotparty.dev.free.gamepoints.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotparty.dev.free.gamepoints.handlers.AccountHandler;
import org.spigotparty.dev.free.gamepoints.GamePoints;

public class PlayerJoinEventListener extends SPListener implements Listener {


    public PlayerJoinEventListener(GamePoints plugin, AccountHandler accountHandler) {
        super(plugin, accountHandler);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
         accountHandler.loadPlayer(e.getPlayer());
    }

}

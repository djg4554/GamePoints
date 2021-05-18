package org.spigotparty.dev.free.gamepoints.listener;

import org.spigotparty.dev.free.gamepoints.GamePoints;
import org.spigotparty.dev.free.gamepoints.handlers.AccountHandler;

public abstract class SPListener {

    protected final GamePoints plugin;
    protected final AccountHandler accountHandler;


    public SPListener(GamePoints plugin, AccountHandler accountHandler) {
        this.plugin = plugin;
        this.accountHandler = accountHandler;
    }


}

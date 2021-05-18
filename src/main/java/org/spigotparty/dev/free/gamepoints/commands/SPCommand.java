package org.spigotparty.dev.free.gamepoints.commands;

import org.bukkit.entity.Player;
import org.spigotparty.dev.free.gamepoints.GamePoints;

public abstract class SPCommand {

    protected GamePoints plugin;

    protected SPCommand(GamePoints plugin) {
        this.plugin = plugin;
    }

    protected boolean canExecute(Player player, String... permissions) {
        if (player.isOp()) return true;
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }

        return true;
    }
}

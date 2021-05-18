package org.spigotparty.dev.free.gamepoints.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotparty.dev.free.gamepoints.GamePoints;
import org.spigotparty.dev.free.gamepoints.PlayerAccount;

public class GPCommand extends SPCommand implements CommandExecutor {


    public GPCommand(GamePoints plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {

            }

            if (args.length < 1) {
                printHelp(sender);
                return true;
            }
            String subCommand = args[0];

            switch (subCommand) {
                case "set": {
                    if (!player.isOp()) {
                        sender.sendMessage(color(plugin.getConfig().getString("messages.not-enough-permission")));
                        return true;
                    }
                    if (args.length < 3) {
                        sender.sendMessage(color("&cError&7: Invalid syntax"));
                        return true;
                    }
                    String recieverName = args[1];
                    OfflinePlayer reciever = Bukkit.getServer().getOfflinePlayer(recieverName);
                    if (!plugin.getAccountManager().hasAccount(player)) {
                        sender.sendMessage(color("&cError&7: Target not exists"));
                        sender.sendMessage(color("&7I'm sorry, but " + recieverName + " has never played on this server!"));
                        return true;
                    }

                    String toParse = args[2];
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(toParse);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(color("&cError&7: Invalid number"));
                        sender.sendMessage(color("&7I'm sorry, but " + toParse + " is not a valid number!"));
                        return true;


                    }
                    PlayerAccount recieverAccount = plugin.getAccountManager().getAccount(reciever);

                    recieverAccount.setAmount(amount);
                    sender.sendMessage(color(plugin.getConfig().getString("messages.setted-amount-to-player").replace("%name%", recieverName).replace("%amount%",""+amount)));
                    if(reciever.isOnline()) {
                        ((Player) reciever).sendMessage(color(plugin.getConfig().getString("messages.setted-amount-reciever").replace("%amount%",""+amount)));
                    } else {
                        plugin.getAccountManager().saveAccount(recieverAccount);
                    }

                    break;
                }
                case "give": {
                    if (!player.isOp()) {
                        sender.sendMessage(color(plugin.getConfig().getString("messages.not-enough-permission")));
                        return true;
                    }
                    if (args.length < 3) {
                        sender.sendMessage(color("&cError&7: Not enough permission"));
                        sender.sendMessage(color("&7I'm sorry, you don't have enough permission to execute this command"));
                        return true;
                    }
                    String recieverName = args[1];
                    OfflinePlayer reciever = Bukkit.getServer().getOfflinePlayer(recieverName);
                    if (!plugin.getAccountManager().hasAccount(player)) {
                        sender.sendMessage(color("&cError&7: Target not exists"));
                        sender.sendMessage(color("&7I'm sorry, but " + recieverName + " has never played on this server!"));
                        return true;
                    }

                    String toParse = args[2];
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(toParse);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(color("&cError&7: Invalid number"));
                        sender.sendMessage(color("&7I'm sorry, but " + toParse + " is not a valid number!"));
                        return true;


                    }

                    PlayerAccount recieverAccount = plugin.getAccountManager().getAccount(reciever);
                    recieverAccount.addAmount(amount);
                    sender.sendMessage(color(plugin.getConfig().getString("messages.added-amount-to-player").replace("%name%", recieverName).replace("%amount%",""+amount)));
                    if(reciever.isOnline()) {
                        ((Player) reciever).sendMessage(color(plugin.getConfig().getString("messages.added-amount-reciever").replace("%amount%",""+amount)));
                    } else {
                        plugin.getAccountManager().saveAccount(recieverAccount);
                    }

                    break;
                }
                case "take": {
                    if (!player.isOp()) {
                        sender.sendMessage(color(plugin.getConfig().getString("messages.not-enough-permission")));
                        return true;
                    }
                    String recieverName = args[1];
                    OfflinePlayer reciever = Bukkit.getServer().getOfflinePlayer(recieverName);
                    if (!plugin.getAccountManager().hasAccount(player)) {
                        sender.sendMessage(color("&cError&7: Target not exists"));
                        sender.sendMessage(color("&7I'm sorry, but " + recieverName + " has never played on this server!"));
                        return true;
                    }

                    String toParse = args[2];
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(toParse);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(color("&cError&7: Invalid number"));
                        sender.sendMessage(color("&7I'm sorry, but " + toParse + " is not a valid number!"));
                        return true;


                    }

                    PlayerAccount recieverAccount = plugin.getAccountManager().getAccount(reciever);
                    if (recieverAccount.getAmount() < amount) {
                        sender.sendMessage(color("&cError&7: Not enough points"));
                        sender.sendMessage(color("&7I'm sorry, but " + recieverName + " has not enough points"));
                        return true;
                    } else {
                        recieverAccount.withdrawAmount(amount);
                        sender.sendMessage(color(plugin.getConfig().getString("messages.taken-amount-from-player").replace("%name%", recieverName).replace("%amount%",""+amount)));
                        if(reciever.isOnline()) {
                            ((Player) reciever).sendMessage(color(plugin.getConfig().getString("messages.taken-amount-reciever").replace("%amount%",""+amount)));
                        } else {
                            plugin.getAccountManager().saveAccount(recieverAccount);
                        }

                        break;
                    }

                }
                case "balance": {
                    if (args.length < 2) {
                        sender.sendMessage(color(plugin.getConfig().getString("messages.personal-balance").replace("%amount%",""+plugin.getAccountManager().getAccount(player).getAmount())));
                        return true;
                    }

                    String recieverName = args[1];
                    OfflinePlayer reciever = Bukkit.getServer().getOfflinePlayer(recieverName);
                    PlayerAccount recieverAccount = plugin.getAccountManager().getAccount(reciever);

                    sender.sendMessage(color(plugin.getConfig().getString("messages.player-balance").replace("%amount%",recieverAccount.getAmount()+"").replace("%name%", recieverName)));
                    break;
                }
                default:
                    printHelp(sender);
                    return true;
            }

        }


        return false;
    }

    private void printHelp(CommandSender sender) {

        sender.sendMessage(color("&8&m  =[&c GamePoints &8&m]=  "));
        sender.sendMessage(color("                            "));
        sender.sendMessage(color("&c/gp set player amount&7:"));
        sender.sendMessage(color(" - &7Set the amount of  game points to player"));
        sender.sendMessage(color("&c/gp give player amount&7:"));
        sender.sendMessage(color(" - &7Gives the amount of  game points to player"));
        sender.sendMessage(color("&c/gp take player amount&7:"));
        sender.sendMessage(color(" - &7Takes the amount of difficult points to player"));
        sender.sendMessage(color("&c/gp balance {player}"));
        sender.sendMessage(color(" - &7Display player points"));
        sender.sendMessage(color(" - &7if the player is not specified, it will show your point"));


    }

    private String color(String toColor) {

        return ChatColor.translateAlternateColorCodes('&', toColor);

    }

}

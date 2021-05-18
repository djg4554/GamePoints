package org.spigotparty.dev.free.gamepoints;

import org.bukkit.OfflinePlayer;

public class PlayerAccount {
    private double amount;
    private OfflinePlayer holder;


    public PlayerAccount(OfflinePlayer holder) {

        this.holder = holder;
        this.amount = 0;

    }

    public PlayerAccount(OfflinePlayer holder, double amount) {
        this.holder = holder;
        this.amount = amount;
    }

    public OfflinePlayer getHolder() {
        return holder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void addAmount(double amount) {this.amount += amount; }

    public void withdrawAmount(double amount) { this.amount-= amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerAccount that = (PlayerAccount) o;

        return holder.equals(that.holder);
    }

    @Override
    public int hashCode() {
        return holder.hashCode();
    }
}

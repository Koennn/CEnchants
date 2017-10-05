package me.koenn.cenchants;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CEnchants extends JavaPlugin {

    public static CEnchants INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}

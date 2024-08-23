package com.github.kaspiandev.pobox;

import com.github.kaspiandev.pobox.config.Config;
import com.github.kaspiandev.pobox.config.Messages;
import com.github.kaspiandev.pobox.data.BoxTable;
import com.github.kaspiandev.pobox.data.Database;
import com.github.kaspiandev.pobox.exception.PluginLoadFailureException;
import org.bukkit.plugin.java.JavaPlugin;

public final class POBox extends JavaPlugin {

    private Database database;
    private BoxTable boxTable;
    private Config config;
    private Messages messages;

    @Override
    public void onEnable() {
        try {
            config = new Config(this);
            messages = new Messages(this);
        } catch (PluginLoadFailureException ex) {
            getPluginLoader().disablePlugin(this);
            throw new RuntimeException(ex);
        }

        database = new Database(this);
        boxTable = new BoxTable(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Config getConf() {
        return config;
    }

    public Messages getMessages() {
        return messages;
    }

    public BoxTable getCommandMailTable() {
        return boxTable;
    }

    public Database getDatabase() {
        return database;
    }

}

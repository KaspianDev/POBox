package com.github.kaspiandev.pobox;

import com.github.kaspiandev.pobox.command.POBoxCommand;
import com.github.kaspiandev.pobox.command.SubCommandRegistry;
import com.github.kaspiandev.pobox.config.Config;
import com.github.kaspiandev.pobox.config.Messages;
import com.github.kaspiandev.pobox.data.BoxTable;
import com.github.kaspiandev.pobox.data.Database;
import com.github.kaspiandev.pobox.data.MailTable;
import com.github.kaspiandev.pobox.exception.PluginLoadFailureException;
import com.github.kaspiandev.pobox.mail.MailManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class POBox extends JavaPlugin {

    private Database database;
    private BoxTable boxTable;
    private MailTable mailTable;
    private MailManager mailManager;
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
        mailTable = new MailTable(this);
        mailManager = new MailManager(this);

        PluginCommand pluginCommand = getCommand("pobox");
        if (pluginCommand != null) {
            SubCommandRegistry subCommandRegistry = new SubCommandRegistry(this);
            POBoxCommand poBoxCommand = new POBoxCommand(this, subCommandRegistry);
            pluginCommand.setExecutor(poBoxCommand);
            pluginCommand.setTabCompleter(poBoxCommand);
        }
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

    public MailManager getMailManager() {
        return mailManager;
    }

    public BoxTable getBoxTable() {
        return boxTable;
    }

    public MailTable getMailTable() {
        return mailTable;
    }

    public Database getDatabase() {
        return database;
    }

}

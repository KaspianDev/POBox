package com.github.kaspiandev.pobox;

import com.github.kaspiandev.pobox.config.Config;
import com.github.kaspiandev.pobox.config.Messages;
import com.github.kaspiandev.pobox.exception.PluginLoadFailureException;
import org.bukkit.plugin.java.JavaPlugin;

public final class POBox extends JavaPlugin {

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

}

package com.github.kaspiandev.pobox.config;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.exception.PluginLoadFailureException;
import com.github.kaspiandev.pobox.util.ColorUtil;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.md_5.bungee.api.chat.BaseComponent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.UnaryOperator;

public class Messages {

    private final POBox plugin;
    private final YamlDocument document;

    public Messages(POBox plugin) throws PluginLoadFailureException {
        this.plugin = plugin;
        InputStream defaults = plugin.getResource("messages.yml");
        if (defaults == null) {
            throw new PluginLoadFailureException("Defaults do not exist. Jar might be corrupted!");
        }
        try {
            this.document = YamlDocument.create(
                    new File(plugin.getDataFolder(), "messages.yml"),
                    defaults,
                    GeneralSettings.builder().setUseDefaults(false).build(),
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new BasicVersioning("version")).build()
            );
        } catch (IOException ex) {
            throw new PluginLoadFailureException("Config could not be loaded.", ex);
        }
    }

    public BaseComponent[] get(Message message) {
        return get(document.getString(message.getPath()));
    }

    public BaseComponent[] get(Message message, UnaryOperator<String> function) {
        return get(document.getString(message.getPath()), function);
    }

    public BaseComponent[] get(String message) {
        return ColorUtil.component(message);
    }

    public BaseComponent[] get(String message, UnaryOperator<String> function) {
        return ColorUtil.component(function.apply(message));
    }

    public YamlDocument getDocument() {
        return document;
    }

}

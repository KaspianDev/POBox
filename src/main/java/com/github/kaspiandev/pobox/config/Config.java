package com.github.kaspiandev.pobox.config;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.exception.PluginLoadFailureException;
import com.github.kaspiandev.pobox.gui.GuiContext;
import com.github.kaspiandev.pobox.gui.GuiLoader;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Config {

    private final POBox plugin;
    private final YamlDocument document;
    private final GuiContext boxGuiContext;

    public Config(POBox plugin) throws PluginLoadFailureException {
        this.plugin = plugin;
        InputStream defaults = plugin.getResource("config.yml");
        if (defaults == null) {
            throw new PluginLoadFailureException("Defaults do not exist. Jar might be corrupted!");
        }
        try {
            this.document = YamlDocument.create(
                    new File(plugin.getDataFolder(), "config.yml"),
                    defaults,
                    GeneralSettings.builder().setUseDefaults(false).build(),
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new BasicVersioning("version")).build()
            );
        } catch (IOException ex) {
            throw new PluginLoadFailureException("Config could not be loaded.", ex);
        }

        this.boxGuiContext = GuiLoader.load(document.getSection("gui.box"));
    }

    public GuiContext getBoxGuiContext() {
        return boxGuiContext;
    }

}

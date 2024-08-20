package com.github.kaspiandev.pobox.command;

import com.github.kaspiandev.pobox.POBox;

import java.util.HashMap;
import java.util.Map;

public class SubCommandRegistry {

    private final POBox plugin;
    private final Map<String, SubCommand> registry;

    public SubCommandRegistry(POBox plugin) {
        this.plugin = plugin;
        this.registry = new HashMap<>();
        load();
    }

    private void load() {
    }

    public Map<String, SubCommand> getRegistry() {
        return registry;
    }

    public SubCommand findById(String id) {
        return registry.get(id);
    }

}

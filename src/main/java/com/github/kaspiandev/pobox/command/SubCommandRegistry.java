package com.github.kaspiandev.pobox.command;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.command.subcommand.SendSubCommand;

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
        SendSubCommand sendSubCommand = new SendSubCommand(plugin);
        registry.put(sendSubCommand.getType().getKey(), sendSubCommand);
    }

    public Map<String, SubCommand> getRegistry() {
        return registry;
    }

    public SubCommand findById(String id) {
        return registry.get(id);
    }

}

package com.github.kaspiandev.pobox.command;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.config.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    private final POBox plugin;
    private final SubCommands type;
    private final String permission;

    protected SubCommand(POBox plugin, SubCommands type) {
        this.plugin = plugin;
        this.type = type;
        this.permission = type.getPermission();
    }

    public SubCommands getType() {
        return type;
    }

    protected abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> suggestions(CommandSender sender, String[] args);

    public void checkPerms(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            if (sender instanceof Player) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_NO_PERMISSIONS));
            }
            return;
        }
        execute(sender, args);
    }

}

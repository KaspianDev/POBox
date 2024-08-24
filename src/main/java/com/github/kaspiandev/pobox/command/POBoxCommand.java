package com.github.kaspiandev.pobox.command;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.config.Message;
import com.github.kaspiandev.pobox.gui.BoxGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class POBoxCommand implements TabExecutor {

    private final POBox plugin;
    private final SubCommandRegistry registry;

    public POBoxCommand(POBox plugin, SubCommandRegistry registry) {
        this.plugin = plugin;
        this.registry = registry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player player) {
                new BoxGui(player, plugin).open();
                return true;
            } else {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_NO_ARGUMENTS));
                return false;
            }
        } else {
            SubCommand cmd = registry.findById(args[0]);
            if (cmd == null) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_INVALID_SUBCOMMAND));
                return false;
            }
            cmd.checkPerms(sender, args);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
                                      String label, String[] args) {
        if (args.length <= 1) {
            return new ArrayList<>(registry.getRegistry().keySet());
        } else {
            SubCommand subCommand = registry.findById(args[0]);
            if (subCommand == null) return null;

            return subCommand.suggestions(sender, args);
        }
    }

}

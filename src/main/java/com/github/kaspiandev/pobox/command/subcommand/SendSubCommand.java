package com.github.kaspiandev.pobox.command.subcommand;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.command.SubCommand;
import com.github.kaspiandev.pobox.command.SubCommands;
import com.github.kaspiandev.pobox.config.Message;
import com.github.kaspiandev.pobox.mail.CommandMail;
import com.github.kaspiandev.pobox.mail.ItemMail;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.StringJoiner;

public class SendSubCommand extends SubCommand {

    public SendSubCommand(POBox plugin) {
        super(plugin, SubCommands.SEND);
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_NO_ARGUMENTS));
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_NO_PLAYER));
            return;
        }

        if (args.length < 3) {
            sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_NO_NAME));
            return;
        }

        String mailName = args[2];
        if (args.length < 4) {
            sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_NO_TYPE));
            return;
        }

        String mailType = args[3];
        if (mailType.equals("command")) {
            if (args.length < 5) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_NO_COMMAND));
                return;
            }

            StringJoiner command = new StringJoiner(" ");
            for (int i = 4; i < args.length; i++) {
                command.add(args[i]);
            }

            plugin.getMailManager().getBox(target).ifPresent((box) -> {
                plugin.getMailManager().sendMail(box, new CommandMail(mailName, command.toString()));
                sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_SENT));
            });
        } else if (mailType.equals("item")) {
            if (!(sender instanceof Player player)) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_ONLY_PLAYERS));
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().isAir()) {
                player.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_NO_ITEM));
                return;
            }

            plugin.getMailManager().getBox(target).ifPresent((box) -> {
                plugin.getMailManager().sendMail(box, new ItemMail(mailName, item));
                player.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_SENT));
            });
        }
    }

    @Override
    public List<String> suggestions(CommandSender sender, String[] args) {
        if (args.length == 3) {
            return List.of("<name>");
        } else if (args.length == 4) {
            return List.of("command", "item");
        } else if (args.length >= 5) {
            if (args[3].equals("command")) {
                return List.of("<command>");
            }
        }
        return List.of();
    }
}

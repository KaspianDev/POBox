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
        if (args.length < 3) {
            sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_NO_ARGUMENTS));
            return;
        }

        // Todo: require name
        // Add null checks
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) return;

        String mailType = args[2];
        if (mailType.equals("command")) {
            if (args.length < 4) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_NO_ARGUMENTS));
                return;
            }

            StringJoiner command = new StringJoiner(" ");
            for (int i = 3; i < args.length; i++) {
                command.add(args[i]);
            }

            plugin.getMailManager().getBox(target).ifPresent((box) -> {
                plugin.getMailManager().addMail(box, new CommandMail("command", command.toString()));
                sender.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_SENT));
            });
        } else if (mailType.equals("item")) {
            if (!(sender instanceof Player player)) {
                sender.spigot().sendMessage(plugin.getMessages().get(Message.COMMAND_ONLY_PLAYERS));
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().isAir()) return; // Item validation

            plugin.getMailManager().getBox(target).ifPresent((box) -> {
                plugin.getMailManager().addMail(box, new ItemMail("item", item));
                player.spigot().sendMessage(plugin.getMessages().get(Message.MAIL_SENT));
            });
        }
    }

    @Override
    public List<String> suggestions(CommandSender sender, String[] args) {
        return List.of();
    }
}

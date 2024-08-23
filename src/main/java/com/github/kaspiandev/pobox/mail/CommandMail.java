package com.github.kaspiandev.pobox.mail;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandMail extends Mail {

    private final String command;

    public CommandMail(String name, String command) {
        super(name);
        this.command = command;
    }

    @Override
    public void claim(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("${player}", player.getName()));
    }

}

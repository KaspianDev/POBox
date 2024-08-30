package com.github.kaspiandev.pobox.mail;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommandMail extends Mail {

    private final String command;

    public CommandMail(String name, String command) {
        super(name);
        this.command = command;
    }

    public CommandMail(String name, String command, Material icon) {
        super(name, icon);
        this.command = command;
    }

    public CommandMail(String name, String sender, String command) {
        super(name, sender);
        this.command = command;
    }

    public CommandMail(String name, Material icon, String command) {
        super(name, icon);
        this.command = command;
    }

    public CommandMail(String name, Material icon, String sender, String command) {
        super(name, icon, sender);
        this.command = command;
    }

    @Override
    public void claim(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("${player}", player.getName()));
    }

}

package com.github.kaspiandev.pobox.mail;

import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class Mail implements Serializable {

    protected final String name;

    public Mail(String name) {
        this.name = name;
    }

    public abstract void claim(Player player);

    public String getName() {
        return name;
    }

}

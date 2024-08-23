package com.github.kaspiandev.pobox.mail;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public abstract class Mail implements Serializable {

    protected final UUID uuid;
    protected final String name;

    public Mail(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public Mail(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public abstract void claim(Player player);

    public String getName() {
        return name;
    }

}

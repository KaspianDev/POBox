package com.github.kaspiandev.pobox.mail;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class Mail implements Serializable {

    protected final String name;
    protected final Material icon;
    protected final String sender;

    public Mail(String name, Material icon, String sender) {
        this.name = name;
        this.icon = (icon.isAir()) ? null : icon;
        this.sender = sender;
    }

    public Mail(String name, Material icon) {
        this(name, icon, null);
    }

    public Mail(String name, String sender) {
        this.name = name;
        this.icon = null;
        this.sender = sender;
    }

    public Mail(String name) {
        this.name = name;
        this.icon = null;
        this.sender = null;
    }

    public abstract void claim(Player player);

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public String getSender() {
        return sender;
    }

}

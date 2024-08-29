package com.github.kaspiandev.pobox.mail;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class Mail implements Serializable {

    protected final String name;
    protected final Material icon;

    public Mail(String name, Material icon) {
        this.name = name;
        this.icon = icon;
    }

    public Mail(String name) {
        this.name = name;
        this.icon = null;
    }

    public abstract void claim(Player player);

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

}

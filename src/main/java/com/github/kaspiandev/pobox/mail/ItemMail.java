package com.github.kaspiandev.pobox.mail;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class ItemMail extends Mail {

    private final ItemStack item;

    public ItemMail(String name, ItemStack item, Material icon) {
        super(name, icon);
        this.item = item;
    }

    public ItemMail(String name, ItemStack item) {
        super(name);
        this.item = item;
    }

    public ItemMail(String name, Material icon, String sender, ItemStack item) {
        super(name, icon, sender);
        this.item = item;
    }

    public ItemMail(String name, Material icon, ItemStack item) {
        super(name, icon);
        this.item = item;
    }

    public ItemMail(String name, String sender, ItemStack item) {
        super(name, sender);
        this.item = item;
    }

    @Override
    public void claim(Player player) {
        PlayerInventory inventory = player.getInventory();
        Map<Integer, ItemStack> leftOvers = inventory.addItem(item);
        for (ItemStack leftOver : leftOvers.values()) {
            player.getWorld().dropItem(player.getLocation(), leftOver);
        }
    }

}

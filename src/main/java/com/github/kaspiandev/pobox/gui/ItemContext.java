package com.github.kaspiandev.pobox.gui;

import org.bukkit.inventory.ItemStack;

public record ItemContext(char key, ItemStack item, String role) {
}

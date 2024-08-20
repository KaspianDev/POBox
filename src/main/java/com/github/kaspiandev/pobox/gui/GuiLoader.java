package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.item.ItemLoader;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiLoader {

    private GuiLoader() {}

    public static GuiContext load(Section section) {
        String title = section.getString("title");
        List<String> mask = section.getStringList("mask");

        Map<Character, ItemContext> items = new HashMap<>();
        Section itemsSection = section.getSection("items");
        for (String itemKey : itemsSection.getRoutesAsStrings(false)) {
            Section itemSection = itemsSection.getSection(itemKey);

            ItemStack item = ItemLoader.load(itemSection);
            String role = itemSection.getString("role");
            items.put(itemKey.charAt(0), new ItemContext(item, role));
        }

        return new GuiContext();
    }

    private void

}

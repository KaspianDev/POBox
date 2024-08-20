package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.gui.exception.GuiLoadingException;
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

        Map<Character, ItemContext> keyToItem = new HashMap<>();
        Section itemsSection = section.getSection("items");
        for (String itemKey : itemsSection.getRoutesAsStrings(false)) {
            Section itemSection = itemsSection.getSection(itemKey);

            ItemStack item = ItemLoader.load(itemSection);
            String role = itemSection.getString("role");
            keyToItem.put(itemKey.charAt(0), new ItemContext(item, role));
        }

        List<ItemContext> items = new ArrayList<>();
        for (String maskLine : mask) {
            if (maskLine.length() != 9) {
                throw new GuiLoadingException("Each mask line must be exactly 9 characters");
            }
            for (int i = 0; i < 9; i++) {
                items.add(keyToItem.get(maskLine.charAt(i)));
            }
        }

        return new GuiContext(title, items);
    }


}

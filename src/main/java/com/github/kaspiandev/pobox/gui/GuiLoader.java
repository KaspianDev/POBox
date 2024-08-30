package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.item.ItemLoader;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiLoader {

    private GuiLoader() {}

    public static GuiContext load(Section section) {
        String title = section.getString("title");
        String[] mask = section.getStringList("mask").toArray(String[]::new);

        List<ItemContext> items = new ArrayList<>();
        Section itemsSection = section.getSection("items");
        for (String itemKey : itemsSection.getRoutesAsStrings(false)) {
            Section itemSection = itemsSection.getSection(itemKey);

            ItemStack item = ItemLoader.load(itemSection);
            String role = itemSection.getString("role");
            String senderLore = itemSection.getString("sender-lore");
            items.add(new ItemContext(itemKey.charAt(0), item, role, (senderLore.isEmpty()) ? null : senderLore));
        }

        return new GuiContext(mask, title, items);
    }


}

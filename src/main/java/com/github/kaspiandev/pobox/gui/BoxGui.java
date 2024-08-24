package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.POBox;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.GuiPageElement;
import de.themoep.inventorygui.InventoryGui;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

public class BoxGui {

    private final Player player;
    private final POBox plugin;

    public BoxGui(Player player, POBox plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public InventoryGui buildGui() {
        GuiContext guiContext = plugin.getConf().getBoxGuiContext();
        InventoryGui gui = new InventoryGui(
                plugin,
                BaseComponent.toLegacyText(plugin.getMessages().get(guiContext.title())),
                guiContext.mask());

        for (ItemContext itemContext : guiContext.items()) {
            String role = itemContext.role();
            if (role == null) {
                gui.addElement(itemContext.key(), itemContext.item());
            } else if (role.equals("next-page")) {
                gui.addElement(new GuiPageElement(itemContext.key(), itemContext.item(), GuiPageElement.PageAction.NEXT));
            } else if (role.equals("previous-page")) {
                gui.addElement(new GuiPageElement(itemContext.key(), itemContext.item(), GuiPageElement.PageAction.PREVIOUS));
            } else if (role.equals("mail")) {
                GuiElementGroup group = new GuiElementGroup(itemContext.key());
//                plugin.getMailManager().getBox(player).thenAccept((commands) -> {
//                    for (String command : commands) {
//                        ItemStack mailItem = itemContext.item();
//                        group.addElement(new StaticGuiElement(itemContext.key(), mailItem, (action) -> {
//                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("${player}", player.getName()));
//                            return true;
//                        }));
//                    }
//                    gui.addElement(group);
//                });
            }
        }


        return gui;
    }

    public void open() {
        buildGui().show(player);
    }
}

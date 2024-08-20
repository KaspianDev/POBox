package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.POBox;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxGui {

    private final Player player;
    private final POBox plugin;
    private final Map<Integer, List<ItemStack>> itemsPerPage;
    private int page = 0;

    public BoxGui(Player player, POBox plugin) {
        this.player = player;
        this.plugin = plugin;
        this.itemsPerPage = new HashMap<>();
        loadPages();
    }

    private void loadPages() {
        GuiContext guiContext = plugin.getConf().getBoxGuiContext();

    }

    public BaseGui buildGui() {
        GuiContext guiContext = plugin.getConf().getBoxGuiContext();
        BaseGui gui = Gui.gui()
                         .rows(guiContext.items().size() / 9)
                         .disableAllInteractions()
                         .create()
                         .updateTitle(BaseComponent.toLegacyText(plugin.getMessages().get(guiContext.title())));


        return gui;
    }

    public void open() {
        buildGui().open(player);
    }
}

package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.POBox;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxGui {

    private final Player player;
    private final POBox plugin;
    private final Map<Integer, List<GuiItem>> itemsPerPage;
    private int page = 0;

    public BoxGui(Player player, POBox plugin) {
        this.player = player;
        this.plugin = plugin;
        this.itemsPerPage = new HashMap<>();
        loadPages();
    }

    // TODO
    private void loadPages() {
        plugin.getCommandMailTable().getCommands(player).thenAccept((commands) -> {
            GuiContext guiContext = plugin.getConf().getBoxGuiContext();
            long mailPerPage = guiContext.items().stream()
                                         .filter((item) -> item.role().equals("mail"))
                                         .count();

            int currentPage = 0;
            int totalPages = (int) Math.max(1, commands.size() / mailPerPage);
            List<GuiItem> currentPageItems = new ArrayList<>();
            for (int i = 0; i < commands.size(); i++) {
                for (ItemContext item : guiContext.items()) {
                    if (item.role().equals("mail")) {
                        GuiItem mailItem = new GuiItem(item.item(), (action) -> {
                            //System.out.println("click " + commands.get(i));
                        });
                    } else {
                        currentPageItems.add(new GuiItem(item.item()));
                    }
                }
            }
        });
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

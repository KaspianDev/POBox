package com.github.kaspiandev.pobox.gui;

import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.entity.Player;

public interface PlayerGui {

    BaseGui buildGui();

    default void open(Player player) {
        buildGui().open(player);
    }

}

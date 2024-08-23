package com.github.kaspiandev.pobox.mail;

import com.github.kaspiandev.pobox.POBox;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MailManager {

    private final POBox plugin;
    private final Map<UUID, Box> playerBoxes;

    public MailManager(POBox plugin) {
        this.plugin = plugin;
        this.playerBoxes = new HashMap<>();
    }

    public void loadPlayerBoxes(Player player) {
        plugin.getBoxTable().loadBox(player).thenAccept((box) -> playerBoxes.put(player.getUniqueId(), box));
    }

    public void unloadPlayerBoxes(Player player) {

    }

}

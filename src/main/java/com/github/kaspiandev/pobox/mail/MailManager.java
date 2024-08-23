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
        plugin.getMailTable().loadMail(player).thenAccept((mail) -> playerBoxes.put(player.getUniqueId(), mail))
    }

    public void unloadPlayerBoxes(Player player) {

    }

}

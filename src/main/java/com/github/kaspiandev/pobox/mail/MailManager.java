package com.github.kaspiandev.pobox.mail;

import com.github.kaspiandev.pobox.POBox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.stream.Collectors;

public class MailManager implements Listener {

    private final POBox plugin;
    private final Map<UUID, Box> playerBoxes;

    public MailManager(POBox plugin) {
        this.plugin = plugin;
        this.playerBoxes = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void loadPlayerBoxes(Player player) {
        plugin.getBoxTable().loadBox(player).thenAccept((box) -> playerBoxes.put(player.getUniqueId(), box));
    }

    public void unloadPlayerBoxes(Player player) {
        playerBoxes.remove(player.getUniqueId());
    }

    public void addMail(Box box, Mail mail) {
        box.addMail(new UniqueMail(mail));
        sync(box);
    }

    public Optional<Box> getBox(Player player) {
        return Optional.ofNullable(playerBoxes.get(player.getUniqueId()));
    }

    public void sync(Box box) {
        List<UniqueMail> boxMail = box.getMailList();

        Set<UUID> boxMailUUIDs = boxMail.stream()
                                        .map(UniqueMail::uuid)
                                        .collect(Collectors.toSet());

        plugin.getMailTable().loadMail(box.getPlayer()).thenAccept((dbMail) -> {
            Set<UUID> dbMailUUIDs = dbMail.stream()
                                          .map(UniqueMail::uuid)
                                          .collect(Collectors.toSet());

            dbMail.stream()
                  .filter((mailFromDb) -> !boxMailUUIDs.contains(mailFromDb.uuid()))
                  .forEach((mailFromDb) -> plugin.getMailTable().deleteMail(mailFromDb));

            boxMail.stream()
                   .filter((mailFromBox) -> !dbMailUUIDs.contains(mailFromBox.uuid()))
                   .forEach((mailFromBox) -> plugin.getMailTable().addMail(box.getPlayer(), mailFromBox));
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        loadPlayerBoxes(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        unloadPlayerBoxes(event.getPlayer());
    }

}

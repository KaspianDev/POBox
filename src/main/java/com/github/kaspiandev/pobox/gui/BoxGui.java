package com.github.kaspiandev.pobox.gui;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.event.MailClaimEvent;
import com.github.kaspiandev.pobox.mail.Mail;
import com.github.kaspiandev.pobox.mail.UniqueMail;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.themoep.inventorygui.*;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BoxGui {

    private static final Cache<UUID, BoxGui> GUI_CACHE;

    static {
        GUI_CACHE = CacheBuilder.newBuilder()
                                .expireAfterWrite(30, TimeUnit.SECONDS)
                                .build();
    }

    private final Player player;
    private final POBox plugin;
    private final GuiContext guiContext;
    private final InventoryGui gui;

    public BoxGui(Player player, POBox plugin) {
        this.player = player;
        this.plugin = plugin;
        this.guiContext = plugin.getConf().getBoxGuiContext();
        this.gui = buildGui();
    }

    public static void open(Player player, POBox plugin) {
        try {
            GUI_CACHE.get(player.getUniqueId(), () -> new BoxGui(player, plugin)).open();
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    public InventoryGui buildGui() {
        InventoryGui gui = new InventoryGui(
                plugin,
                "",
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
                gui.addElement(new DynamicGuiElement(itemContext.key(), () -> {
                    GuiElementGroup group = new GuiElementGroup(itemContext.key());
                    plugin.getMailManager().getBox(player).ifPresent((box) -> {
                        for (UniqueMail uniqueMail : box.getMailList()) {
                            ItemStack mailItem = itemContext.item().clone();
                            ItemMeta mailItemMeta = mailItem.getItemMeta();
                            Mail mail = uniqueMail.mail();
                            if (mailItemMeta != null) {
                                mailItemMeta.setDisplayName(mailItemMeta.getDisplayName()
                                                                        .replace("${mailName}", mail.getName()));
                                mailItem.setItemMeta(mailItemMeta);
                            }

                            Material icon = mail.getIcon();
                            if (icon != null) {
                                mailItem.setType(icon);
                            }

                            group.addElement(new StaticGuiElement(itemContext.key(), mailItem, (action) -> {
                                MailClaimEvent mailClaimEvent = new MailClaimEvent(player, uniqueMail);
                                Bukkit.getPluginManager().callEvent(mailClaimEvent);
                                if (mailClaimEvent.isCancelled()) return true;
                                mail.claim(player);
                                plugin.getMailManager().removeMail(box, uniqueMail);
                                gui.draw(player, true, false);
                                return true;
                            }));
                        }
                    });
                    return group;
                }));
            }
        }

        String title = guiContext.title();
        gui.setTitle(BaseComponent.toLegacyText(plugin.getMessages().get(title)));

        return gui;
    }

    private void open() {
        gui.show(player);
    }

}

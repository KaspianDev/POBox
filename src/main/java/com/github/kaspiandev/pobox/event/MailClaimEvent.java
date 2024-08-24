package com.github.kaspiandev.pobox.event;

import com.github.kaspiandev.pobox.mail.UniqueMail;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class MailClaimEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UniqueMail mail;
    private boolean cancelled;

    public MailClaimEvent(@NotNull Player who, UniqueMail mail) {
        super(who);
        this.mail = mail;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public UniqueMail getMail() {
        return mail;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}

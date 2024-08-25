package com.github.kaspiandev.pobox.mail;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {

    private final Player player;
    private final List<UniqueMail> mailList;

    public Box(Player player) {
        this.player = player;
        this.mailList = new ArrayList<>();
    }

    void addMail(UniqueMail mail) {
        mailList.add(mail);
    }

    void removeMail(UniqueMail mail) {
        mailList.remove(mail);
    }

    public List<UniqueMail> getMailList() {
        return Collections.unmodifiableList(mailList);
    }

    public Player getPlayer() {
        return player;
    }

}

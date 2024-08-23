package com.github.kaspiandev.pobox.mail;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Box {

    private final Player player;
    private final List<Mail> mailList;

    public Box(Player player) {
        this.player = player;
        this.mailList = new ArrayList<>();
    }

    public void addMail(Mail mail) {
        mailList.add(mail);
    }

    public List<Mail> getMailList() {
        return mailList;
    }
    
}

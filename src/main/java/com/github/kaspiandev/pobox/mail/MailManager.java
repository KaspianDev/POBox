package com.github.kaspiandev.pobox.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MailManager {

    private final Map<UUID, Box> playerBoxes;

    public MailManager() {
        this.playerBoxes = new HashMap<>();
    }

}

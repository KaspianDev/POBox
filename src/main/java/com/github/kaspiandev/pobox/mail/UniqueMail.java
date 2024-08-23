package com.github.kaspiandev.pobox.mail;

import java.util.UUID;

public record UniqueMail(UUID uuid, Mail mail) {

    public UniqueMail(Mail mail) {
        this(UUID.randomUUID(), mail);
    }

}

package com.github.kaspiandev.pobox.mail;

import java.util.UUID;

public record UniqueMail(UUID uuid, Mail mail) {
}

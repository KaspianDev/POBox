package com.github.kaspiandev.pobox.mail.io;

import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.IOException;
import java.io.InputStream;

public class MailObjectInputStream extends BukkitObjectInputStream {

    public MailObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

}

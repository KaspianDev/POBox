package com.github.kaspiandev.pobox.mail.io;

import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.IOException;
import java.io.OutputStream;

public class MailObjectOutputStream extends BukkitObjectOutputStream {
    
    public MailObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

}

package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;

public class MailTable extends Table {

    private static final String CREATE_MAIL_TABLE = """
            CREATE TABLE IF NOT EXISTS mail (
                player_uuid CHAR(36),
                mail BLOB,
                FOREIGN KEY(player_uuid) REFERENCES player_box(player_uuid)
            )
            """;

    public MailTable(POBox plugin) {
        super(plugin, CREATE_MAIL_TABLE);
    }

}

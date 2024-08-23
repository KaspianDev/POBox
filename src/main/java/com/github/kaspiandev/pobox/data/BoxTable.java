package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;

public class BoxTable extends Table {

    private static final String CREATE_PLAYER_BOX_TABLE = """
            CREATE TABLE IF NOT EXISTS player_box (
                player_uuid CHAR(36),
                PRIMARY KEY(player_uuid)
            )
            """;

    public BoxTable(POBox plugin) {
        super(plugin, CREATE_PLAYER_BOX_TABLE);
    }

}

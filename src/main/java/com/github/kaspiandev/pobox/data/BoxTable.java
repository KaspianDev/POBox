package com.github.kaspiandev.pobox.data;

public class BoxTable extends Table {

    private static final String CREATE_BOX_TABLE = """
            CREATE TABLE IF NOT EXISTS box (
                id_box INT,
                player_uuid CHAR(36),
                PRIMARY KEY(id_box)
            );
            """;

    public BoxTable(Database database) {
        super(database, CREATE_BOX_TABLE);
    }

}

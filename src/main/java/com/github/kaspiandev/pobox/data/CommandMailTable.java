package com.github.kaspiandev.pobox.data;

public class CommandMailTable extends Table {

    private static final String CREATE_MAIL_TABLE = """
            CREATE TABLE IF NOT EXISTS command_mail (
                id_box INT,
                command VARCHAR(255)
            );
            """;

    public CommandMailTable(Database database) {
        super(database, CREATE_MAIL_TABLE);
    }

}

package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.mail.Box;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<Box> loadBox(Player player) {
        return plugin.getMailTable().loadMail(player).thenApply((mail) -> {
            Box box = new Box(player);
            mail.forEach(box::addMail);
            return box;
        });
    }

}

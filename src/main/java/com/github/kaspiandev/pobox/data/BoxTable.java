package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.mail.Box;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class BoxTable extends Table {

    private static final String CREATE_PLAYER_BOX_TABLE = """
            CREATE TABLE IF NOT EXISTS player_box (
                player_uuid CHAR(36),
                PRIMARY KEY(player_uuid)
            )
            """;
    private static final String CREATE_IF_NOT_EXISTS = """
            INSERT OR IGNORE INTO player_box (player_uuid)
            VALUES (?)
            """;

    public BoxTable(POBox plugin) {
        super(plugin, CREATE_PLAYER_BOX_TABLE);
    }

    public CompletableFuture<Void> createIfNotExists(Player player) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(CREATE_IF_NOT_EXISTS)) {
                    statement.setString(1, player.getUniqueId().toString());
                    statement.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

    public CompletableFuture<Box> loadBox(Player player) {
        return createIfNotExists(player).thenComposeAsync((result) ->
                plugin.getMailTable().loadMail(player).thenApply((mail) -> {
                    Box box = new Box(player);
                    mail.forEach(box::addMail);
                    return box;
                })
        );
    }

}

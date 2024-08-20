package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CommandMailTable extends Table {

    private static final String CREATE_COMMAND_MAIL_TABLE = """
            CREATE TABLE IF NOT EXISTS command_mail (
                player_uuid CHAR(36),
                command VARCHAR(255)
            );
            """;
    private static final String ADD_MAIL = """
            INSERT INTO command_mail (player_uuid, command)
            VALUES (?, ?)
            """;
    private static final String GET_MAIL = """
            SELECT command FROM command_mail
            WHERE player_uuid = ?
            """;

    public CommandMailTable(POBox plugin) {
        super(plugin, CREATE_COMMAND_MAIL_TABLE);
    }

    public CompletableFuture<Void> addMail(UUID uuid, String command) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(ADD_MAIL)) {
                    statement.setString(1, uuid.toString());
                    statement.setString(2, command);
                    statement.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occured.", ex);
            }
        });
    }

    public CompletableFuture<List<String>> getCommands(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(GET_MAIL)) {
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet = statement.executeQuery();

                    List<String> commands = new ArrayList<>();
                    while (resultSet.next()) {
                        commands.add(resultSet.getString(1));
                    }
                    return commands;
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occured.", ex);
            }
        });
    }

}

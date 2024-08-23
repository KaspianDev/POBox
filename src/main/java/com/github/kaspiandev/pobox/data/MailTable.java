package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.mail.Mail;
import com.github.kaspiandev.pobox.mail.io.MailObjectInputStream;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MailTable extends Table {

    private static final String CREATE_MAIL_TABLE = """
            CREATE TABLE IF NOT EXISTS mail (
                player_uuid CHAR(36),
                mail BLOB,
                FOREIGN KEY(player_uuid) REFERENCES player_box(player_uuid)
            )
            """;

    private static final String GET_MAIL = """
            SELECT mail from mail
            WHERE player_uuid = ?
            """;

    public MailTable(POBox plugin) {
        super(plugin, CREATE_MAIL_TABLE);
    }

    public CompletableFuture<List<Mail>> loadMail(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(GET_MAIL)) {
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet = statement.executeQuery();

                    List<Mail> mail = new ArrayList<>();
                    if (resultSet.next()) {
                        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(resultSet.getBytes(1));
                             MailObjectInputStream dataInput = new MailObjectInputStream(inputStream)) {
                            mail.add((Mail) dataInput.readObject());
                        } catch (IOException | ClassNotFoundException ignored) {}
                    }
                    resultSet.close();
                    return mail;
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

}

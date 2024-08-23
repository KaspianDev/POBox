package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import com.github.kaspiandev.pobox.mail.Mail;
import com.github.kaspiandev.pobox.mail.UniqueMail;
import com.github.kaspiandev.pobox.mail.io.MailObjectInputStream;
import com.github.kaspiandev.pobox.mail.io.MailObjectOutputStream;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MailTable extends Table {

    private static final String CREATE_MAIL_TABLE = """
            CREATE TABLE IF NOT EXISTS mail (
                uuid CHAR(36),
                player_uuid CHAR(36),
                mail BLOB,
                FOREIGN KEY(player_uuid) REFERENCES player_box(player_uuid)
            )
            """;
    private static final String GET_PLAYER_MAIL = """
            SELECT uuid, mail from mail
            WHERE player_uuid = ?
            """;
    private static final String GET_MAIL = """
            SELECT mail from mail
            WHERE uuid = ?
            """;
    private static final String ADD_MAIL = """
            INSERT INTO mail (player_uuid, uuid, mail)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_MAIL = """
            DELETE FROM mail
            WHERE mail = ?
            """;

    public MailTable(POBox plugin) {
        super(plugin, CREATE_MAIL_TABLE);
    }

    public CompletableFuture<List<UniqueMail>> loadMail(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(GET_PLAYER_MAIL)) {
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet = statement.executeQuery();

                    List<UniqueMail> mailList = new ArrayList<>();
                    while (resultSet.next()) {
                        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(resultSet.getBytes(2));
                             MailObjectInputStream dataInput = new MailObjectInputStream(inputStream)) {
                            UUID uuid = UUID.fromString(resultSet.getString(1));
                            Mail mail = (Mail) dataInput.readObject();
                            mailList.add(new UniqueMail(uuid, mail));
                        } catch (IOException | ClassNotFoundException ignored) {}
                    }
                    resultSet.close();
                    return mailList;
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

    public CompletableFuture<Optional<Mail>> findMail(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(GET_MAIL)) {
                    statement.setString(1, uuid.toString());
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(resultSet.getBytes(1));
                             MailObjectInputStream dataInput = new MailObjectInputStream(inputStream)) {
                            resultSet.close();
                            return Optional.of((Mail) dataInput.readObject());
                        } catch (IOException | ClassNotFoundException ignored) {}
                    }

                    resultSet.close();
                    return Optional.empty();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

    public CompletableFuture<Void> addMail(Player player, UniqueMail mail) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(ADD_MAIL)) {
                    statement.setString(1, player.getUniqueId().toString());

                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                         MailObjectOutputStream dataOutput = new MailObjectOutputStream(outputStream)) {
                        statement.setString(2, mail.uuid().toString());
                        dataOutput.writeObject(mail.mail());
                        statement.setBytes(3, outputStream.toByteArray());
                        statement.executeUpdate();
                    } catch (IOException ignored) {}
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

    public CompletableFuture<Void> deleteMail(Player player, Mail mail) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = plugin.getDatabase().getSQLConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(DELETE_MAIL)) {
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                         MailObjectOutputStream dataOutput = new MailObjectOutputStream(outputStream)) {
                        dataOutput.writeObject(mail);
                        statement.setBytes(1, outputStream.toByteArray());
                        statement.executeUpdate();
                    } catch (IOException ignored) {}
                }
            } catch (SQLException ex) {
                throw new RuntimeException("An SQL exception occurred.", ex);
            }
        });
    }

}

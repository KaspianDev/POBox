package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Database {

    private final File dbFile;
    private final List<Table> tables;
    private final HikariDataSource hikariDataSource;

    public Database(POBox plugin) {
        this.dbFile = new File(plugin.getDataFolder(), "pobox.db");
        try {
            dbFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.tables = new ArrayList<>();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setJdbcUrl("jdbc:sqlite:" + dbFile);
        hikariConfig.setMaximumPoolSize(6);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getSQLConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getSQLConnection()) {
                try (Statement statement = connection.createStatement()) {
                    for (Table table : tables) {
                        statement.executeUpdate(table.getTableStatement());
                    }
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void registerTable(Table table) {
        tables.add(table);
    }

}

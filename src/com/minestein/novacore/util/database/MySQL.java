package com.minestein.novacore.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Copyright MineStein 2014©
 * All files included within the project are subject under the standard
 * GNU license. Any and all assets are the sole property of MineStein.
 */
public class MySQL {

    /**
     * The connection to the database.
     */
    static Connection connection;

    /**
     * Gets the connection of NovaUniverse's database.
     *
     * @return The connection.
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("databaseURL");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connection;
    }
}

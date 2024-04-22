package org.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() throws SQLException {
        final String DB_URL =  "jdbc:postgresql://localhost/rbBase1";
        final String Tovar = "postgres";
        final String PASS = "123";

        try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());

            Connection connection = DriverManager.getConnection(DB_URL, Tovar, PASS);
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        throw new SQLException();
    }
}

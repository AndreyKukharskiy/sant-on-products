package org.util;

import org.model.Product;

import java.sql.*;

public class JdbcConnectionHelper {
    public void getConnection(Product tovar) {
        Connection conn1 = null;
        final String DB_URL =  "jdbc:postgresql://localhost/rbBase";
        final String Tovar = "postgres";
        final String PASS = "123";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            conn1 = DriverManager.getConnection(DB_URL, Tovar, PASS);
            Statement statement = conn1.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tovar;");


            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String articul = resultSet.getString("articul");
                System.out.println("Name: " + name + ", Articul: " + articul);
            }

            if (conn1 != null) {
                System.out.println("Connected to database #1");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

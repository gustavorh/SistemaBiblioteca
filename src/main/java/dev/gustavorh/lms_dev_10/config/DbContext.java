package dev.gustavorh.lms_dev_10.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=LMS_Testing;trustServerCertificate=true";
    private static String username = "sa";
    private static String password = "^.r}fPr~A/Kj";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

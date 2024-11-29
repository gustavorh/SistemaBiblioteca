package dev.gustavorh.lms_dev_10.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=LMS_Testing;trustServerCertificate=true";
    private static final String username = "sa";
    private static final String password = "2[VrkrN627mX";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

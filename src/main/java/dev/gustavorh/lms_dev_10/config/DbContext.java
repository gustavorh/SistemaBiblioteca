package dev.gustavorh.lms_dev_10.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=LibraryManagement;trustServerCertificate=true";
    private static final String username = "dba_lms";
    private static final String password = "^.r}fPr~A/Kj";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

package com.example.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnection class handles PostgreSQL database connections
 * Provides methods to establish and close database connections
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";
    
    /**
     * Establishes a connection to the PostgreSQL database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        
        return DriverManager.getConnection(DB_URL, props);
    }
    
    /**
     * Closes the database connection
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}

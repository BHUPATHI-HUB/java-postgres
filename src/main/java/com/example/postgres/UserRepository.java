package com.example.postgres;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository class for database operations on User table
 * Provides CRUD operations for user management
 */
public class UserRepository {
    
    /**
     * Creates a new user in the database
     * @param name User's name
     * @param email User's email
     * @return User ID of created user
     */
    public int createUser(String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    /**
     * Retrieves a user by ID
     * @param userId User ID to retrieve
     * @return User object or null if not found
     */
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
                );
            }
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    /**
     * Retrieves all users from the database
     * @return List of all users
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection conn = DatabaseConnection.getConnection();
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return users;
    }
    
    /**
     * Updates user information
     * @param userId User ID to update
     * @param name New name
     * @param email New email
     */
    public void updateUser(int userId, String name, String email) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    /**
     * Deletes a user from the database
     * @param userId User ID to delete
     */
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
    
    /**
     * Inner class representing a User entity
     */
    public static class User {
        private int id;
        private String name;
        private String email;
        
        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }
}

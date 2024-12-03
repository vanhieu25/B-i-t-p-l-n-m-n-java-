package com.example.gamestoremanagement.service;

import com.example.gamestoremanagement.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=GameStoreDB;encrypt=true;trustServerCertificate=true";    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123456$";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }
    }

    public UserService() {
        // Initialize pre-created admin accounts
        addUser(new User("admin1", "1", "ADMIN"));
        addUser(new User("admin2", "2", "ADMIN"));
        addUser(new User("admin3", "3", "ADMIN"));
    }

    public User findUserByUsername(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public void addUser(User user) {
        if (findUserByUsername(user.getUsername()) == null) {
            String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
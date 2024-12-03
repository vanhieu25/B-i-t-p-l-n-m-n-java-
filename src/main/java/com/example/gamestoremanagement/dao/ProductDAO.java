package com.example.gamestoremanagement.dao;

import com.example.gamestoremanagement.model.Product;

import java.sql.*;

public class ProductDAO {
    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (name, type, price, launchDate, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getType());
            stmt.setDouble(3, product.getPrice());
            stmt.setDate(4, Date.valueOf(product.getLaunchDate()));
            stmt.setString(5, product.getCategory());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE Products SET name = ?, type = ?, price = ?, launchDate = ?, category = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getType());
            stmt.setDouble(3, product.getPrice());
            stmt.setDate(4, Date.valueOf(product.getLaunchDate()));
            stmt.setString(5, product.getCategory());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public Product findProductByName(String name) {
        String sql = "SELECT * FROM Products WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"), // Add this line
                        rs.getString("category"),
                        rs.getDate("launchDate").toString()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
}
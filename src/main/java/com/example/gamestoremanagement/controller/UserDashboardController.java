package com.example.gamestoremanagement.controller;

import com.example.gamestoremanagement.model.Product;
import com.example.gamestoremanagement.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserDashboardController {
    public Label welcomeLabel;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TextField searchField;
    @FXML
    private TextField priceField;

    private final ProductService productService = ProductService.getInstance();
    private ObservableList<Product> productList;

    @FXML
    public void initialize() {
        try {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            loadProducts();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            showAlert("Initialization Error", "An error occurred during initialization.");
        }
    }

    private void loadProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            productList = FXCollections.observableArrayList(products);
            productTable.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            showAlert("Load Products Error", "An error occurred while loading products.");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String keyword = searchField.getText();

            Double price = null;
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                // Handle the case where price is not a valid number
            }
            List<Product> products = productService.searchProducts(keyword, price);
            productList.setAll(products);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            showAlert("Search Error", "An error occurred while searching for products.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gamestoremanagement/fxml/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) productTable.getScene().getWindow(); // Use an existing node to get the stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            showAlert("Logout Error", "An error occurred while trying to log out.");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            showAlert("Unexpected Error", "An unexpected error occurred.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
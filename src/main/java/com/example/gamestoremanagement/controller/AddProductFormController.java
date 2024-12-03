package com.example.gamestoremanagement.controller;

import com.example.gamestoremanagement.model.Product;
import com.example.gamestoremanagement.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductFormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField launchDateField;

    private final ProductService productService = ProductService.getInstance();

    @FXML
    private void handleAddProduct() {
        try {
            String name = nameField.getText();
            String type = typeField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryField.getText();
            String launchDate = launchDateField.getText();

            Product newProduct = new Product(0, name, type, price, quantity, category, launchDate);
            productService.addProduct(newProduct);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter valid product details.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
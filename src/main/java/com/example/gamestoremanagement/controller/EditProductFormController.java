package com.example.gamestoremanagement.controller;

import com.example.gamestoremanagement.model.Product;
import com.example.gamestoremanagement.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductFormController {

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

    private Product product;
    private final ProductService productService = ProductService.getInstance();

    public void setProduct(Product product) {
        this.product = product;
        nameField.setText(product.getName());
        typeField.setText(product.getType());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        categoryField.setText(product.getCategory());
        launchDateField.setText(product.getLaunchDate());
    }

    @FXML
    private void handleSaveProduct() {
        try {
            product.setName(nameField.getText());
            product.setType(typeField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setQuantity(Integer.parseInt(quantityField.getText()));
            product.setCategory(categoryField.getText());
            product.setLaunchDate(launchDateField.getText());

            productService.updateProduct(product);

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
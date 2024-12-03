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
import java.util.Comparator;
import java.util.List;

public class AdminDashboardController extends BaseController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, String> launchDateColumn;
    @FXML
    private TextField searchField;
    @FXML
    private TextField priceField;
    @FXML
    private Label countLabel;

    private final ProductService productService = ProductService.getInstance();
    private ObservableList<Product> productList;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        launchDateColumn.setCellValueFactory(new PropertyValueFactory<>("launchDate"));

        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        productList = FXCollections.observableArrayList(products);
        productTable.setItems(productList);
        updateCount();
    }

    @FXML
    private void handleAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gamestoremanagement/fxml/AddProductForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadProducts(); // Refresh the product list after adding a new product
        } catch (IOException e) {
            e.printStackTrace(System.err);
            showAlert("Error", "An error occurred while trying to add a product.");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Product");
            confirmationAlert.setHeaderText("Are you sure you want to delete this product?");
            confirmationAlert.setContentText("Product: " + selectedProduct.getName());

            ButtonType deleteButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == deleteButton) {
                    productService.deleteProduct(selectedProduct);
                    productList.remove(selectedProduct);
                    updateCount();
                }
            });
        } else {
            showAlert("No product selected", "Please select a product to delete.");
        }
    }
    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gamestoremanagement/fxml/EditProductForm.fxml"));
                Parent root = loader.load();

                // Pass the selected product to the EditProductFormController
                EditProductFormController controller = loader.getController();
                controller.setProduct(selectedProduct);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                loadProducts(); // Refresh the product list after editing a product
            } catch (IOException e) {
                e.printStackTrace(System.err);
                showAlert("Error", "An error occurred while trying to edit the product.");
            }
        } else {
            showAlert("No product selected", "Please select a product to edit.");
        }
    }
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        Double price = null;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            // Handle the case where price is not a valid number
        }
        List<Product> products = productService.searchProducts(keyword, price);
        productList.setAll(products);
    }

    @FXML
    private void handleSortByName() {
        productList.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
    }

    @FXML
    private void handleSortByPrice() {
        productList.sort(Comparator.comparingDouble(Product::getPrice));
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
            showAlert("Error", "An error occurred while trying to log out.");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            showAlert("Error", "An unexpected error occurred.");
        }
    }

    private void updateCount() {
        countLabel.setText("Total Products: " + productList.size());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
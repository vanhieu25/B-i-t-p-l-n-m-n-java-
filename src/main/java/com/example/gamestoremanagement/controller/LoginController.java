package com.example.gamestoremanagement.controller;

import com.example.gamestoremanagement.model.User;
import com.example.gamestoremanagement.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> loginRightComboBox;
    @FXML
    private Label errorMessage;

    private final UserService userService = new UserService();

    @FXML
    public void handleLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = loginRightComboBox.getValue();

            User user = userService.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {
                errorMessage.setVisible(false);
                if (role.equalsIgnoreCase("ADMIN")) {
                    switchToAdminDashboard();
                } else {
                    switchToUserDashboard();
                }
            } else {
                errorMessage.setText("Invalid credentials");
                errorMessage.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            errorMessage.setText("An error occurred during login");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    private void switchToRegister() {
        switchView("/com/example/gamestoremanagement/fxml/RegisterView.fxml");
    }

    @FXML
    private void switchToLogin() {
        switchView("/com/example/gamestoremanagement/fxml/LoginView.fxml");
    }

    private void switchView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private void switchToAdminDashboard() {
        switchView("/com/example/gamestoremanagement/fxml/AdminDashboard.fxml");
    }

    private void switchToUserDashboard() {
        switchView("/com/example/gamestoremanagement/fxml/UserDashboard.fxml");
    }
}
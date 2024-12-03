package com.example.gamestoremanagement.controller;

import com.example.gamestoremanagement.model.User;
import com.example.gamestoremanagement.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label messageLabel;

    private final UserService userService = new UserService();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.equals(confirmPassword)) {
            if (userService.findUserByUsername(username) == null) {
                User newUser = new User(username, password, "USER");
                userService.addUser(newUser);
                messageLabel.setText("Registration successful. Redirecting to login...");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                messageLabel.setVisible(true);

                // Redirect to login
                switchToLogin();
            } else {
                messageLabel.setText("Username already exists");
                messageLabel.setTextFill(javafx.scene.paint.Color.RED);
                messageLabel.setVisible(true);
            }
        } else {
            messageLabel.setText("Passwords do not match");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setVisible(true);
        }
    }

    @FXML
    private void switchToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gamestoremanagement/fxml/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
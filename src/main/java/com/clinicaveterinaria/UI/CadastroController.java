package com.clinicaveterinaria.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class CadastroController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField crmvField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // CRMV validation pattern (Format: XX 00000)
    private static final Pattern CRMV_PATTERN =
            Pattern.compile("^[A-Z]{2}\\s\\d{5}$");


    private void setupFieldValidation() {
        // Email field validation
        emailField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !emailField.getText().isEmpty()) {
                if (!isValidEmail(emailField.getText())) {
                    emailField.setStyle("-fx-border-color: red;");
                } else {
                    emailField.setStyle("");
                }
            }
        });

        // CRMV field validation
        crmvField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !crmvField.getText().isEmpty()) {
                if (!isValidCRMV(crmvField.getText())) {
                    crmvField.setStyle("-fx-border-color: red;");
                } else {
                    crmvField.setStyle("");
                }
            }
        });
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        // Clear previous messages
        hideMessages();

        // Get field values
        String name = nameField.getText().trim();
        String crmv = crmvField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (!validateInput(name, crmv, email, password)) {
            return;
        }

        // Check if user already exists
        if (userExists(email)) {
            showError("Este email já está cadastrado.");
            return;
        }

        // Create user account
        if (createUser(name, crmv, email, password)) {
            showSuccess("Conta criada com sucesso!");

            // Wait a moment and then redirect to login
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Wait 2 seconds
                    javafx.application.Platform.runLater(() -> {
                        handleBackToLogin(event);
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            showError("Erro ao criar conta. Tente novamente.");
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login - AUMIAU SAUDE");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar tela de login.");
        }
    }

    private boolean validateInput(String name, String crmv, String email, String password) {
        if (name.isEmpty()) {
            showError("Nome é obrigatório.");
            nameField.requestFocus();
            return false;
        }

        if (name.length() < 3) {
            showError("Nome deve ter pelo menos 3 caracteres.");
            nameField.requestFocus();
            return false;
        }

        if (crmv.isEmpty()) {
            showError("CRMV é obrigatório.");
            crmvField.requestFocus();
            return false;
        }

        if (!isValidCRMV(crmv)) {
            showError("CRMV deve estar no formato: XX 00000");
            crmvField.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            showError("Email é obrigatório.");
            emailField.requestFocus();
            return false;
        }

        if (!isValidEmail(email)) {
            showError("Email inválido.");
            emailField.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            showError("Senha é obrigatória.");
            passwordField.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            showError("Senha deve ter pelo menos 6 caracteres.");
            passwordField.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidCRMV(String crmv) {
        return CRMV_PATTERN.matcher(crmv.toUpperCase()).matches();
    }

    private boolean userExists(String email) {
        // Mock check - replace with actual database query
        // This would typically check if the email already exists in your database
        return false;
    }

    private boolean createUser(String name, String crmv, String email, String password) {
        // Mock user creation - replace with actual database insertion
        // This is where you would:
        // 1. Hash the password
        // 2. Insert user into database
        // 3. Handle any database errors

        try {
            // Simulate database operation
            Thread.sleep(500);

            // For now, just return true to simulate successful creation
            // In reality, you would return the result of your database operation
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        successLabel.setVisible(false);
    }

    private void showSuccess(String message) {
        successLabel.setText(message);
        successLabel.setVisible(true);
        errorLabel.setVisible(false);
    }

    private void hideMessages() {
        errorLabel.setVisible(false);
        successLabel.setVisible(false);
    }

    // Method to clear all fields
    public void clearFields() {
        nameField.clear();
        crmvField.clear();
        emailField.clear();
        passwordField.clear();
        hideMessages();

        // Reset field styles
        emailField.setStyle("");
        crmvField.setStyle("");
    }
}

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

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        errorLabel.setVisible(false);

        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, preencha todos os campos.");
            return;
        }

        // adicionar futuras validações
        // Altenticação (Exemplo)
        if (authenticateUser(username, password)) {
            // Determinar o tipo de user
            String userType = getUserType(username);
            redirectToMainScreen(event, userType);
        } else {
            showError("Usuário ou senha incorretos.");
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cadastro de Usuário - AUMIAU SAUDE");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar tela de cadastro.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        // verificação de credenciais

        // (demonstrativo)
        if ("veterinario@aumiau.com".equals(username) && "vet123".equals(password)) {
            return true;
        } else if ("atendente@aumiau.com".equals(username) && "att123".equals(password)) {
            return true;
        }
        return false;
    }

    private String getUserType(String username) {
        // tipo de usuario (demonstrativo) - substituir deopis
        if (username.contains("veterinario")) {
            return "VETERINARIO";
        } else if (username.contains("atendente")) {
            return "ATENDENTE";
        }
        return "ATENDENTE"; // Default
    }

    private void redirectToMainScreen(ActionEvent event, String userType) {
        try {
            String fxmlPath;
            String windowTitle;

            // determinar para qual tela o usuário será redirecionado dependnedo do tipo
            if ("VETERINARIO".equals(userType)) {
                fxmlPath = "tela_principal_veterinario.fxml";
                windowTitle = "Sistema Veterinário - AUMIAU SAUDE";
            } else {
                fxmlPath = "tela_principal_atendente.fxml";
                windowTitle = "Sistema Atendente - AUMIAU SAUDE";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(windowTitle);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar tela principal.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}

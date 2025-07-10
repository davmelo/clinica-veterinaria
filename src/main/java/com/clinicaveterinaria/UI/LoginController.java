package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
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
    public Button loginButton;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Hyperlink cadastroLink;

    @FXML
    private Label errorLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    @FXML
    private void login(ActionEvent event) {
        String username = userField.getText().trim();
        String password = senhaField.getText();

        errorLabel.setVisible(false);

        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor, preencha todos os campos.");
            return;
        }

        if ("atendente@aumiau.com".equals(username) && "att123".equals(password)) {
            redirectToMainScreen(event, "ATENDENTE");
            return;
        }

        try {
            VeterinarioRespostaDTO veterinarioLogado = clinica.autenticarVeterinario(username, password);

            if (veterinarioLogado != null) {
                redirectToMainScreen(event, "VETERINARIO"); // Ou 'ATENDENTE' se você adicionar autenticação para atendentes
            } else {
                showError("Usuário ou senha incorretos.");
            }
        } catch (Exception e) {
            showError("Ocorreu um erro ao tentar fazer login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro_principal_tela.fxml"));
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

    private void redirectToMainScreen(ActionEvent event, String userType) {
        try {
            String fxmlPath;
            String windowTitle;

            // determinar para qual tela o usuário será redirecionado dependnedo do tipo
            if ("VETERINARIO".equals(userType)) {
                fxmlPath = "veterinario_tela.fxml";
                windowTitle = "Sistema Veterinário - AUMIAU SAUDE";
            } else {
                fxmlPath = "atendente_tela.fxml";
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

    public void entrar(ActionEvent actionEvent) {

    }
}

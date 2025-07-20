package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import com.clinicaveterinaria.negocio.UsuarioVeterinario;
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
    private TextField userField; // Este é o campo de texto do usuário (identificador/username)

    @FXML
    private PasswordField senhaField;

    @FXML
    private Hyperlink cadastroLink;

    @FXML
    private Label errorLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    @FXML
    private void login(ActionEvent event) {
        String identificador = userField.getText().trim();
        String senha = senhaField.getText();

        errorLabel.setText("");
        errorLabel.setVisible(false);

        if (identificador.isEmpty() || senha.isEmpty()) {
            showError("Por favor, preencha todos os campos.");
            return;
        }

        // Login de atendente para testes
        if ("atendente@aumiau.com".equals(identificador) && "att123".equals(senha)) {
            redirectToMainScreen(event, "ATENDENTE");
            return;
        }

        try {
            VeterinarioRespostaDTO veterinarioDTO = clinica.autenticarVeterinario(identificador, senha);

            if (veterinarioDTO != null) {
                Veterinario veterinarioAutenticado = clinica.buscarVeterinarioEntidade(veterinarioDTO.crmv());

                if (veterinarioAutenticado != null) {
                    UsuarioVeterinario.setVeterinarioLogado(veterinarioAutenticado);
                    redirectToMainScreen(event, "VETERINARIO");
                } else {
                    showError("Erro interno: Veterinário não pode ser carregado completamente.");
                }

            } else {
                showError("Usuário ou senha incorretos.");
            }
        } catch (Exception e) {
            showError("Ocorreu um erro durante o login: " + e.getMessage());
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

            if ("VETERINARIO".equals(userType)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("veterinario_tela.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Sistema Veterinário - AUMIAU SAUDE");
                stage.show();

            } else if ("ATENDENTE".equals(userType)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("atendente_tela.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Sistema Atendente - AUMIAU SAUDE");
                stage.show();
            } else {
                fxmlPath = "login_tela.fxml";
                windowTitle = "Erro de Autenticação";
            }

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

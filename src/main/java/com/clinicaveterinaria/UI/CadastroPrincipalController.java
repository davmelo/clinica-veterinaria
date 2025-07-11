package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.VeterinarioRequisicaoDTO; // Para criar o DTO de veterinário
import com.clinicaveterinaria.negocio.ServidorClinica; // Para acessar a camada de negócio
import javafx.application.Platform; // Para garantir que o redirecionamento ocorra na Thread de UI
import java.util.ArrayList; // Para a lista de especialidades
import java.util.Arrays; // Adicionar este import para usar Arrays.asList()

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

public class CadastroPrincipalController {

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField sobrenomeField;


    @FXML
    private TextField crmvField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private TextField especialidadeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    // Validação Email - Padrão mais flexível
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Validação CRMV - Mais flexível para aceitar diferentes formatos
    private static final Pattern CRMV_PATTERN =
            Pattern.compile("^[A-Z]{2}\\s*\\d{4,5}$");

    // Validação telefone - Mais flexível para aceitar diferentes formatos
    private static final Pattern TELEFONE_PATTERN =
            Pattern.compile("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$");

    private boolean isValidTelefone(String telefone) {
        // Remove todos os caracteres não numéricos para validar
        String numeroLimpo = telefone.replaceAll("[^0-9]", "");
        // Verifica se tem 10 ou 11 dígitos (com ou sem 9º dígito)
        return numeroLimpo.length() == 10 || numeroLimpo.length() == 11;
    }

    @FXML
    private void criarConta(ActionEvent event) {
        hideMessages();

        String nome = nomeField.getText().trim();
        String sobrenome = sobrenomeField.getText().trim();
        String crmv = crmvField.getText().trim();
        String email = emailField.getText().trim();
        String password = senhaField.getText(); //Apenas para simulação de UI
        String especialidade = especialidadeField.getText().trim();
        String telefone = telefoneField.getText().trim();

        // Debug - Remova estas linhas após testar
        System.out.println("Nome: '" + nome + "'");
        System.out.println("Sobrenome: '" + sobrenome + "'");
        System.out.println("CRMV: '" + crmv + "'");
        System.out.println("Email: '" + email + "'");
        System.out.println("Telefone: '" + telefone + "'");
        System.out.println("Especialidade: '" + especialidade + "'");

        if (!validateInput(nome, sobrenome, crmv, telefone, email, password, especialidade)) {
            return;
        }

        try {
            // Normaliza e verifica se o CRMV já existe antes de tentar cadastrar
            String crmvNormalizadoParaBusca = crmv.toUpperCase().replaceAll("\\s+", "");
            if (clinica.buscarVeterinario(crmvNormalizadoParaBusca) != null) {
                showError("Um veterinário com este CRMV já está cadastrado.");
                crmvField.requestFocus();
                return;
            }

            if (createUserAndVeterinario(nome, sobrenome, crmv, email, password, telefone, especialidade)) {
                showSuccess();
                clearFields();

                // Redirecionamento para a tela de Login após 2 segundos
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        Platform.runLater(() -> voltarLogin(event));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        showError("Operação interrompida.");
                    }
                }).start();
            } else {
                showError("Erro ao criar conta. Tente novamente.");
            }
        } catch (Exception e) {
            showError("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void voltarLogin(ActionEvent event) {
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

    private boolean validateInput(String name, String sobrenome, String crmv, String telefone, String email, String password, String especialidade) {
        if (name.isEmpty()) {
            showError("Nome é obrigatório.");
            nomeField.requestFocus();
            return false;
        }
        if (name.length() < 3) {
            showError("Nome deve ter pelo menos 3 caracteres.");
            nomeField.requestFocus();
            return false;
        }

        if (sobrenome.isEmpty()) {
            showError("Sobrenome é obrigatório.");
            sobrenomeField.requestFocus();
            return false;
        }
        if (sobrenome.length() < 3) {
            showError("Sobrenome deve ter pelo menos 3 caracteres.");
            sobrenomeField.requestFocus();
            return false;
        }

        if (crmv.isEmpty()) {
            showError("CRMV é obrigatório.");
            crmvField.requestFocus();
            return false;
        }
        if (!isValidCRMV(crmv)) {
            showError("CRMV deve estar no formato: XX 00000 ou XX00000.");
            crmvField.requestFocus();
            return false;
        }

        if (telefone.isEmpty()) {
            showError("Telefone é obrigatório.");
            telefoneField.requestFocus();
            return false;
        }
        if (!isValidTelefone(telefone)) {
            showError("Formato de telefone inválido. Ex: (00) 00000-0000 ou 00900000000.");
            telefoneField.requestFocus();
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
            senhaField.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            showError("Senha deve ter pelo menos 6 caracteres.");
            senhaField.requestFocus();
            return false;
        }

        if (especialidade.isEmpty()) {
            showError("Especialidade é obrigatória.");
            especialidadeField.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidCRMV(String crmv) {
        // Converte para maiúscula e remove espaços extras
        String crmvFormatado = crmv.toUpperCase().replaceAll("\\s+", ""); // Remover TODOS os espaços para CRMV_PATTERN
        return CRMV_PATTERN.matcher(crmvFormatado).matches();
    }

    private boolean createUserAndVeterinario(String name, String sobrenome, String crmv, String email, String password, String telefone, String especialidade) {
        try {
            VeterinarioRequisicaoDTO veterinarioDTO = new VeterinarioRequisicaoDTO(
                    null, // ID será gerado
                    name,
                    sobrenome,
                    crmv,
                    email,
                    password,
                    telefone,
                    new ArrayList<>(Arrays.asList(especialidade))
            );

            clinica.cadastrarVeterinario(veterinarioDTO);

            //Essa linha só vai aparecer se o cadastro for bem-sucedido.
            System.out.println("Veterinário cadastrado via UI com sucesso! Nome: " + name + " " + sobrenome + ", CRMV: " + crmv + ", Especialidade: " + especialidade);

            return true;
        } catch (Exception e) {
            // Captura qualquer exceção que venha do ServidorClinica, como por exemplo CRMV já existe
            System.err.println("Erro ao cadastrar veterinário: " + e.getMessage());
            e.printStackTrace();
            return false; // Retorna falso em caso de erro
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        successLabel.setVisible(false);
    }

    private void showSuccess() {
        successLabel.setText("Conta criada com sucesso!");
        successLabel.setVisible(true);
        errorLabel.setVisible(false);
    }

    private void hideMessages() {
        errorLabel.setVisible(false);
        successLabel.setVisible(false);
    }

    private void clearFields() {
        nomeField.clear();
        sobrenomeField.clear();
        crmvField.clear();
        emailField.clear();
        telefoneField.clear();
        senhaField.clear();
        especialidadeField.clear();
    }
}
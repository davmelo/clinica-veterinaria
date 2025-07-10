package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.ClienteRequisicaoDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastrarClienteController {

    @FXML
    public Button voltarButton;

    @FXML
    public Button addButton;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField sobrenomeField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField telefoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField enderecoField;

    @FXML private Label messageLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    public void voltarCliente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível retornar à tela de clientes.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCadastrarCliente(ActionEvent event) {
        messageLabel.setText("");
        messageLabel.setTextFill(javafx.scene.paint.Color.RED);

        String nome = nomeField.getText().trim();
        String sobrenome = sobrenomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String email = emailField.getText().trim();
        String endereco = enderecoField.getText().trim();

        if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
            messageLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        if (!cpf.matches("\\d{11}")) {
            messageLabel.setText("CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        ClienteRequisicaoDTO clienteDTO = new ClienteRequisicaoDTO(
                null,
                nome,
                sobrenome,
                cpf,
                telefone,
                email,
                endereco
        );

        try {
            clinica.cadastrarCliente(clienteDTO);
            messageLabel.setText("Cliente cadastrado com sucesso!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            limparCamposCliente();

        } catch (Exception e) {
            messageLabel.setText("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCamposCliente() {
        nomeField.clear();
        sobrenomeField.clear();
        cpfField.clear();
        telefoneField.clear();
        emailField.clear();
        enderecoField.clear();
        messageLabel.setText("");
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cadastrarAnimal(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("cadastrar_animal.fxml"));
            Stage stage = (Stage) addButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de cadastro de animal.");
            e.printStackTrace();
        }
    }
}

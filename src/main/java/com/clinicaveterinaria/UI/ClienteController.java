package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
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

public class ClienteController {

    @FXML
    private Button criarClienteButton;

    @FXML
    private Button editarClienteButton;

    @FXML
    private Button voltarButton;

    @FXML
    private TextField idClienteSearchField;

    @FXML
    private Button buscarClienteButton;
    @FXML
    private Label clienteNomeLabel;
    @FXML
    private Label clienteSobrenomeLabel;
    @FXML
    private Label clienteCpfLabel;
    @FXML
    private Label clienteTelefoneLabel;
    @FXML
    private Label clienteEmailLabel;
    @FXML
    private Label clienteEnderecoLabel;
    @FXML
    private Label statusMessageLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    private ClienteRespostaDTO clienteEncontrado;

    @FXML
    private void voltarAtendente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("atendente_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Menu Principal");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível retornar à tela do atendente.");
            e.printStackTrace();
        }
    }

    public void cadastrarCliente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastrar_cliente.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) criarClienteButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de cadastro de cliente.");
            e.printStackTrace();
        }
    }

    public void editarCliente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_cliente.fxml"));
            Parent root = loader.load();

            EditarClienteController editarClienteController = loader.getController();
            editarClienteController.setCliente(clienteEncontrado);

            Stage stage = (Stage) editarClienteButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de edição de cliente.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscarCliente(ActionEvent event) {
        String termoBuscaCpf = idClienteSearchField.getText().trim();
        statusMessageLabel.setText("");

        if (termoBuscaCpf.isEmpty()) {
            statusMessageLabel.setText("Por favor, digite o CPF do cliente.");
            statusMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
            limparCamposClienteExibicao();
            return;
        }

        if (!termoBuscaCpf.matches("\\d{11}")) {
            statusMessageLabel.setText("CPF deve conter exatamente 11 dígitos numéricos.");
            statusMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
            limparCamposClienteExibicao();
            return;
        }

        try {
            clienteEncontrado = clinica.buscarCliente(termoBuscaCpf);

            if (clienteEncontrado != null) {
                clienteNomeLabel.setText(clienteEncontrado.nome());
                clienteSobrenomeLabel.setText(clienteEncontrado.sobrenome());
                clienteCpfLabel.setText(clienteEncontrado.cpf());
                clienteTelefoneLabel.setText(clienteEncontrado.telefone());
                clienteEmailLabel.setText(clienteEncontrado.email());
                clienteEnderecoLabel.setText(clienteEncontrado.endereco());
                editarClienteButton.setDisable(false);
                statusMessageLabel.setText("Cliente encontrado!");
                statusMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                statusMessageLabel.setText("Cliente com CPF " + termoBuscaCpf + " não encontrado.");
                statusMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
                limparCamposClienteExibicao();
            }
        } catch (Exception e) {
            statusMessageLabel.setText("Erro ao buscar cliente: " + e.getMessage());
            statusMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
            e.printStackTrace();
            limparCamposClienteExibicao();
        }
    }

    private void limparCamposClienteExibicao() {
        clienteNomeLabel.setText("[Nome]");
        clienteSobrenomeLabel.setText("[Sobrenome]");
        clienteCpfLabel.setText("[CPF]");
        clienteTelefoneLabel.setText("[Telefone]");
        clienteEmailLabel.setText("[Email]");
        clienteEnderecoLabel.setText("[Endereço]");
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
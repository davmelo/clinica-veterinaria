package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.ClienteRequisicaoDTO;
import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class EditarClienteController {

    @FXML
    public Button voltarButton;
    @FXML
    private TextField nomeCompletoField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField enderecoField;

    @FXML
    private Button salvarButton;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    private ClienteRespostaDTO cliente;

    public void setCliente(ClienteRespostaDTO cliente) {
        this.cliente = cliente;
        nomeCompletoField.setText(cliente.nome() + " " + cliente.sobrenome());
        cpfField.setText(cliente.cpf());
        telefoneField.setText(cliente.telefone());
        emailField.setText(cliente.email());
        enderecoField.setText(cliente.endereco());
    }

    public void atualizarCliente(ActionEvent actionEvent) {

        String telefone = telefoneField.getText().trim();
        String email = emailField.getText().trim();
        String endereco = enderecoField.getText().trim();

        if (telefone.isEmpty() || email.isEmpty() || endereco.isEmpty()) {
            //messageLabel.setText("Por favor, preencha todos os campos.");
            return;
        }


        ClienteRequisicaoDTO clienteDTO = new ClienteRequisicaoDTO(
                null,
                cliente.nome(),
                cliente.sobrenome(),
                cliente.cpf(),
                telefone,
                email,
                endereco
        );

        try {
            clinica.atualizarCliente(clienteDTO);

        } catch (Exception e) {
            //messageLabel.setText("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void voltarTelaCliente(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.VeterinarioRequisicaoDTO;
import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.UsuarioVeterinario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditarVeterinarioController {

    @FXML
    public Button voltarButton;

    @FXML
    private RadioButton verSenhaButton;

    @FXML
    private TextField nomeCompletoField, crmvField, emailField, telefoneField, senhaField, senhaVisivelField;

    private VeterinarioRespostaDTO vetLogado = UsuarioVeterinario.getVeterinarioLogado().paraDTO();

    private ServidorClinica clinica = ServidorClinica.getInstance();

    @FXML
    public void initialize() {
        nomeCompletoField.setText(vetLogado.nome() + " " + vetLogado.sobrenome());
        crmvField.setText(vetLogado.crmv());
        emailField.setText(vetLogado.email());
        telefoneField.setText(vetLogado.telefone());
        senhaField.textProperty().bindBidirectional(senhaVisivelField.textProperty());
        senhaField.setText(UsuarioVeterinario.getVeterinarioLogado().getSenha());
    }

    public void voltarVet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("veterinario_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verSenha(ActionEvent event) {
        boolean verSenha = verSenhaButton.isSelected();

        senhaVisivelField.setVisible(verSenha);
        senhaVisivelField.setManaged(verSenha);

        senhaField.setVisible(!verSenha);
        senhaField.setManaged(!verSenha);
    }

    public void atualizarVeterinario() {
        String novoTelefone = telefoneField.getText().trim();
        String novaSenha = senhaField.getText().trim();

        VeterinarioRequisicaoDTO vetAtualizado = new VeterinarioRequisicaoDTO(
                UsuarioVeterinario.getVeterinarioLogado().getId(),
                vetLogado.nome(),
                vetLogado.sobrenome(),
                vetLogado.crmv(),
                vetLogado.email(),
                novaSenha,
                novoTelefone,
                UsuarioVeterinario.getVeterinarioLogado().getEspecialidades()
                );

        try {
            clinica.atualizarVeterinario(vetLogado.crmv(), vetAtualizado);

        } catch (Exception e) {
            //messageLabel.setText("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

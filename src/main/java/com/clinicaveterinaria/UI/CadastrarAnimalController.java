package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AnimalRequisicaoDTO;
import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CadastrarAnimalController {

    @FXML
    public Button voltarButton;

    @FXML
    private TextField tutorCpfField;

    @FXML
    private Label tutorNomeLabel;

    @FXML
    private TextField nomeAnimalField;

    @FXML
    private TextField especieField;

    @FXML
    private TextField racaField;

    @FXML
    private DatePicker dataNascimentoPicker;

    @FXML
    private TextField pesoField;

    @FXML
    private TextField identificacaoField;

    @FXML private Label messageLabel; // Para exibir mensagens de erro/sucesso

    private ServidorClinica clinica = ServidorClinica.getInstance();

    private String tutorCpfAssociado; // Para armazenar o CPF do tutor validado

    public void setTutorCpf(String cpf) {
        if (cpf != null && !cpf.isEmpty()) {
            tutorCpfField.setText(cpf);
            handleBuscarTutor(null); // Tenta buscar o tutor automaticamente ao definir o CPF
            tutorCpfField.setEditable(false); // Torna o campo não editável se o CPF já veio preenchido
        }
    }

    public void handleVoltar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastrar_cliente.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível retornar à tela anterior.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscarTutor(ActionEvent event) {
        String cpfBusca = tutorCpfField.getText().trim();
        tutorNomeLabel.setText("");
        messageLabel.setText("");

        if (cpfBusca.isEmpty()) {
            messageLabel.setText("Por favor, digite o CPF do tutor.");
            return;
        }
        if (!cpfBusca.matches("\\d{11}")) {
            messageLabel.setText("CPF deve conter 11 dígitos numéricos.");
            return;
        }

        try {
            ClienteRespostaDTO tutorEncontrado = clinica.buscarCliente(cpfBusca);

            if (tutorEncontrado != null) {
                tutorNomeLabel.setText("Tutor: " + tutorEncontrado.nome() + " " + tutorEncontrado.sobrenome());
                tutorNomeLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                tutorCpfAssociado = tutorEncontrado.cpf();
            } else {
                tutorNomeLabel.setText("Tutor não encontrado.");
                tutorNomeLabel.setTextFill(javafx.scene.paint.Color.RED);
                tutorCpfAssociado = null;
            }
        } catch (Exception e) {
            messageLabel.setText("Erro ao buscar tutor: " + e.getMessage());
            e.printStackTrace();
            tutorCpfAssociado = null;
        }
    }

    @FXML
    private void handleCadastrarAnimal(ActionEvent event) {
        messageLabel.setText("");
        messageLabel.setTextFill(javafx.scene.paint.Color.RED);

        if (tutorCpfAssociado == null || tutorCpfAssociado.isEmpty()) {
            messageLabel.setText("Por favor, busque e associe um tutor válido primeiro.");
            return;
        }

        String nome = nomeAnimalField.getText().trim();
        String especie = especieField.getText().trim();
        String raca = racaField.getText().trim();
        LocalDate dataNascimento = dataNascimentoPicker.getValue();
        String pesoStr = pesoField.getText().trim();
        String identificacao = identificacaoField.getText().trim();

        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || dataNascimento == null || pesoStr.isEmpty() || identificacao.isEmpty()) {
            messageLabel.setText("Por favor, preencha todos os campos do animal.");
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
            if (peso <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            messageLabel.setText("Peso inválido. Digite um número maior que zero.");
            return;
        }

        AnimalRequisicaoDTO animalDTO = new AnimalRequisicaoDTO(
                null, // Id é gerada no Controlador
                tutorCpfAssociado,
                nome,
                especie,
                raca,
                dataNascimento,
                peso,
                identificacao
        );

        try {
            clinica.cadastrarAnimal(animalDTO);
            messageLabel.setText("Animal cadastrado com sucesso!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            limparCamposAnimal();

        } catch (Exception e) {
            messageLabel.setText("Erro ao cadastrar animal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCamposAnimal() {
        nomeAnimalField.clear();
        especieField.clear();
        racaField.clear();
        dataNascimentoPicker.setValue(null);
        pesoField.clear();
        identificacaoField.clear();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

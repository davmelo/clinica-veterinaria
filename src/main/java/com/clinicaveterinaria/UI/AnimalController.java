package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AnimalRespostaDTO;
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

import java.io.IOException;

public class AnimalController {

    @FXML
    private Button voltarButton;

    @FXML
    private TextField animalIdField;
    
    @FXML
    private Button buscarButton;

    @FXML
    private Label nomeLabel;
    @FXML
    private Label especieLabel;
    @FXML
    private Label racaLabel;
    @FXML
    private Label idadeLabel;
    @FXML
    private Label pesoLabel;
    @FXML
    private Label identificacaoLabel;
    @FXML
    private Label tutorLabel;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("atendente_tela.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuscar(ActionEvent event) {
        Long animalId = Long.valueOf(animalIdField.getText().trim());

        try {
            AnimalRespostaDTO animalEncontrado = clinica.buscarAnimal(animalId);

            if (animalEncontrado != null) {
                nomeLabel.setText(animalEncontrado.nome());
                especieLabel.setText(animalEncontrado.especie());
                racaLabel.setText(animalEncontrado.raca());
                idadeLabel.setText(String.valueOf(animalEncontrado.idade()));
                pesoLabel.setText(String.valueOf(animalEncontrado.peso()));
                identificacaoLabel.setText(animalEncontrado.identificacao());
                tutorLabel.setText(animalEncontrado.tutorNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
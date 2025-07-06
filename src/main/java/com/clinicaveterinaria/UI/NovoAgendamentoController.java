package com.clinicaveterinaria.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class NovoAgendamentoController {

    public void handleCancelar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestao_agendamentos.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Menu Principal");
            stage.show();

        } catch (IOException e) {
            showErrorAlert();
            e.printStackTrace();
        }
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro no Sistema");
        alert.setContentText("Não foi possível carregar a tela");
        alert.showAndWait();
    }
}

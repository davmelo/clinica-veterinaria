package com.clinicaveterinaria.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AtendenteController {

    @FXML
    private void handleAgendamentos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agendamentos_gestao.fxml"));
            Parent root = loader.load();

            AgendamentosController controller = loader.getController();
            controller.configurarPerfil("ATENDENTE");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Agendamentos");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de agendamentos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Clientes");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de clientes.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnimais(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("animal_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Animais");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de animais.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Sair do Sistema");
        alert.setContentText("Tem certeza que deseja sair do sistema?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login_tela.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Sistema Clínica Veterinária - Login");
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Erro ao sair", "Não foi possível retornar à tela de login.");
                e.printStackTrace();
            }
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
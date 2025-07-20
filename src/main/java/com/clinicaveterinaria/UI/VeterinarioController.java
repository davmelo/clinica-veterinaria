package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
import com.clinicaveterinaria.negocio.UsuarioVeterinario;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;

public class VeterinarioController {

    @Setter
    private VeterinarioRespostaDTO vetLogado;

    @FXML
    public Button sairButton;

    @FXML
    public Button perfilButton;

    @FXML
    public Button gerenciarAgendaButton; // Adicione este fx:id ao seu FXML

//    public void sairLogin(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmação");
//        alert.setHeaderText("Sair do Sistema");
//        alert.setContentText("Tem certeza que deseja sair do sistema?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("login_tela.fxml"));
//                Parent root = loader.load();
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.setTitle("Sistema Clínica Veterinária - Login");
//                stage.show();
//
//            } catch (IOException e) {
//                showErrorAlert("Erro ao sair", "Não foi possível retornar à tela de login.");
//                e.printStackTrace();
//            }
//        }
//    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void editarVet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editar_veterinario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) perfilButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gerenciarAgenda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agenda_veterinario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) gerenciarAgendaButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Veterinário - Gerenciar Agenda");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro de Navegação", "Não foi possível abrir a tela de Gerenciar Agenda.");
            e.printStackTrace();
        }
    }

    @FXML
    private void sairLogin(ActionEvent event) {
        UsuarioVeterinario.limparSessao();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) sairButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Veterinário - Login");
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erro de Navegação", "Não foi possível retornar à tela de login.");
            e.printStackTrace();
        }
    }

    public void verAgendamentos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agendamentos_gestao.fxml"));
            Parent root = loader.load();

            AgendamentosController controller = loader.getController();
            controller.configurarPerfil("VETERINARIO");

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
}
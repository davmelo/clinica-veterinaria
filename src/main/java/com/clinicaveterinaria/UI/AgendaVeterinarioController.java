package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.DispoAgendaRequisicaoDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.UsuarioVeterinario;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgendaVeterinarioController {

    @FXML private Button voltarButton;
    @FXML private Label veterinarioInfoLabel;
    @FXML private ComboBox<String> diaSemanaComboBox;
    @FXML private ComboBox<String> horarioComboBox;
    @FXML private Button adicionarDisponibilidadeButton;
    @FXML private Button removerDisponibilidadeButton;
    @FXML private ListView<String> disponibilidadeListView; // Para exibir a agenda atual
    @FXML private Label statusMessageLabel;

    private final ServidorClinica clinica = ServidorClinica.getInstance();
    private String crmvVeterinarioLogado; // CRMV do veterinário que a agenda está sendo modificada
    private Veterinario veterinarioAtual; // Entidade do veterinário atual

    @FXML
    public void initialize() {
//        try {
//            List<Veterinario> vets = clinica.listarTodosVeterinarios();
//            if (vets != null && !vets.isEmpty()) {
//                veterinarioAtual = vets.get(0); // Pega o primeiro veterinário
//                crmvVeterinarioLogado = veterinarioAtual.getCrmv();
//                veterinarioInfoLabel.setText("Veterinário: " + crmvVeterinarioLogado + " - " + veterinarioAtual.getNome() + " " + veterinarioAtual.getSobrenome());
//            } else {
//                showStatusMessage("Nenhum veterinário cadastrado. Não é possível gerenciar agenda.", true);
//                return;
//            }
//        } catch (Exception e) {
//            showStatusMessage("Erro ao carregar informações do veterinário: " + e.getMessage(), true);
//            e.printStackTrace();
//            return;
//        }

        veterinarioAtual = UsuarioVeterinario.getVeterinarioLogado();
        crmvVeterinarioLogado = veterinarioAtual.getCrmv();
        veterinarioInfoLabel.setText("Veterinário: " + crmvVeterinarioLogado + " - " + veterinarioAtual.getNome() + " " + veterinarioAtual.getSobrenome());
        //Dias da Semana
        diaSemanaComboBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(DiaSemana.values()).map(Enum::name).collect(Collectors.toList())
        ));
        diaSemanaComboBox.getSelectionModel().selectFirst(); // Seleciona o primeiro dia como padrão

        // Horários a cada 30 minutos
        ObservableList<String> horas = FXCollections.observableArrayList();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 30) {
                horas.add(String.format("%02d:%02d", h, m));
            }
        }
        horarioComboBox.setItems(horas);
        horarioComboBox.getSelectionModel().select("09:00"); // Seleciona 9h como um horário padrão

        carregarDisponibilidadeAtual();
    }

    public void setCrmvVeterinarioLogado(String crmv) {
        this.crmvVeterinarioLogado = crmv;
    }

    // Carrega e exibe a disponibilidade atual do veterinário
    private void carregarDisponibilidadeAtual() {
        if (crmvVeterinarioLogado == null || crmvVeterinarioLogado.isEmpty()) {
            showStatusMessage("CRMV do veterinário não definido.", true);
            disponibilidadeListView.setItems(FXCollections.emptyObservableList());
            return;
        }

        try {
            DisponibilidadeAgenda agenda = clinica.getDisponibilidadeVeterinario(crmvVeterinarioLogado);

            if (agenda != null && agenda.getHorariosDisponiveis() != null) {
                ObservableList<String> itensAgenda = FXCollections.observableArrayList();
                for (Map.Entry<DiaSemana, List<LocalTime>> entry : agenda.getHorariosDisponiveis().entrySet()) {
                    DiaSemana dia = entry.getKey();
                    List<LocalTime> horarios = entry.getValue();
                    horarios.sort(LocalTime::compareTo);
                    for (LocalTime hora : horarios) {
                        itensAgenda.add(dia.name() + " às " + hora.toString());
                    }
                }
                disponibilidadeListView.setItems(itensAgenda);
                showStatusMessage("Agenda carregada.", false);
            } else {
                disponibilidadeListView.setItems(FXCollections.emptyObservableList());
                showStatusMessage("Agenda vazia ou não encontrada para este veterinário.", false);
            }
        } catch (Exception e) {
            showStatusMessage("Erro ao carregar agenda: " + e.getMessage(), true);
            e.printStackTrace();
            disponibilidadeListView.setItems(FXCollections.emptyObservableList());
        }
    }

    @FXML
    private void handleAddDisponibilidade(ActionEvent event) {
        String diaSelecionadoStr = diaSemanaComboBox.getSelectionModel().getSelectedItem();
        String horarioSelecionadoStr = horarioComboBox.getSelectionModel().getSelectedItem();

        if (diaSelecionadoStr == null || horarioSelecionadoStr == null) {
            showStatusMessage("Selecione o dia e o horário para adicionar.", true);
            return;
        }

        DiaSemana dia = DiaSemana.valueOf(diaSelecionadoStr);
        LocalTime horario = LocalTime.parse(horarioSelecionadoStr);

        try {
            DispoAgendaRequisicaoDTO dispoDTO = new DispoAgendaRequisicaoDTO(dia, horario);
            clinica.adiconarDispoAgenda(crmvVeterinarioLogado, dispoDTO); // Adiciona disponibilidade
            showStatusMessage("Horário adicionado com sucesso!", false);
            carregarDisponibilidadeAtual(); // Recarrega a lista para mostrar a mudança
        } catch (Exception e) {
            showStatusMessage("Erro ao adicionar horário: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveDisponibilidade(ActionEvent event) {
        String itemSelecionado = disponibilidadeListView.getSelectionModel().getSelectedItem();
        if (itemSelecionado == null) {
            showStatusMessage("Selecione um horário na lista para remover.", true);
            return;
        }

        try {
            String[] partes = itemSelecionado.split(" às ");
            DiaSemana dia = DiaSemana.valueOf(partes[0]);
            LocalTime horario = LocalTime.parse(partes[1]);

            // Chamar o serviço para remover a disponibilidade
            clinica.removerDispoAgenda(crmvVeterinarioLogado, dia, horario); // Remove disponibilidade
            showStatusMessage("Horário removido com sucesso!", false);
            carregarDisponibilidadeAtual(); // Recarrega a lista
        } catch (Exception e) {
            showStatusMessage("Erro ao remover horário: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("veterinario_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Veterinário - AUMIAU SAUDE");
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erro de Navegação", "Não foi possível retornar à tela anterior.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAtualizar(ActionEvent event) {
        carregarDisponibilidadeAtual();
    }

    private void showStatusMessage(String message, boolean isError) {
        statusMessageLabel.setText(message);
        statusMessageLabel.setTextFill(isError ? Color.RED : Color.GREEN);
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
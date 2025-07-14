package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AgendamentoRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AgendamentosController {

    @FXML
    public Button atenderButton;

    @FXML private Button voltarButton;

    // Filtros
    @FXML
    private DatePicker datePicker;

    @FXML
    private Button hojeButton;

    @FXML
    private Button estaSemanaButton;

    @FXML
    private Button todosButton;

    // Tabela de Agendamentos
    @FXML
    private TableView<AgendamentoRespostaDTO> appointmentTable;

    @FXML
    private TableColumn<AgendamentoRespostaDTO, LocalDateTime> horaColumn;

    @FXML
    private TableColumn<AgendamentoRespostaDTO, String> clienteColumn;

    @FXML
    private TableColumn<AgendamentoRespostaDTO, String> animalColumn;

    @FXML
    private TableColumn<AgendamentoRespostaDTO, String> veterinarioColumn;

    @FXML
    private TableColumn<AgendamentoRespostaDTO, AgendamentoStatus> statusColumn;

    // Detalhes do Agendamento
    @FXML
    private Label idLabel;

    @FXML
    private Label dataHoraLabel;

    @FXML
    private Label clienteLabel;

    @FXML
    private Label animalLabel;

    @FXML
    private Label veterinarioLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea observacoesTextArea;

    // Botões
    @FXML
    private Button novoAgendamentoButton;

    @FXML
    private Button editarAgendamentoButton;

    @FXML
    private Button cancelarAgendamentoButton;

    @FXML
    private Button confirmarAgendamentoButton;

    @FXML
    private Button reagendarButton;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    private String perfilUsuario;

    public void configurarPerfil(String perfil) {
        this.perfilUsuario = perfil;

        if ("VETERINARIO".equals(perfil) ) {
            novoAgendamentoButton.setVisible(false);
            editarAgendamentoButton.setVisible(false);
        }
        if ("ATENDENTE".equals(perfil) ) {
            atenderButton.setVisible(false);
        }
    }

    @FXML
    public void initialize() {
        horaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().dataAgendamento()));
        clienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nomeCliente()));
        animalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nomeAnimal()));
        veterinarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nomeVeterinario()));
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().status()));

        horaColumn.setCellFactory(column -> new TableCell<AgendamentoRespostaDTO, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        datePicker.setOnAction(this::handleFilterByDate);
        hojeButton.setOnAction(this::handleFilterToday);
        estaSemanaButton.setOnAction(this::handleFilterThisWeek);
        todosButton.setOnAction(this::handleFilterAll);

        appointmentTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        displayAppointmentDetails(newSelection);
                    } else {
                        clearAppointmentDetails();
                    }
                    //clearAppointmentDetails();
                });
        loadAppointments(null);
    }

    private void loadAppointments(LocalDate filterDate) {
        List<Agendamento> agendamentosEntidade;

        try {
            if (filterDate != null) {
                agendamentosEntidade = clinica.buscarAgendamentosPorDia(filterDate);
            } else {
                agendamentosEntidade = clinica.listarTodosAgendamentos();
            }

            if (agendamentosEntidade != null) {
                ObservableList<AgendamentoRespostaDTO> agendamentosDTO = FXCollections.observableArrayList(
                        agendamentosEntidade.stream()
                                .map(agendamento -> agendamento.paraDTO())
                                .collect(Collectors.toList())
                );
                appointmentTable.setItems(agendamentosDTO);
            } else {
                appointmentTable.setItems(FXCollections.emptyObservableList());
            }
        } catch (Exception e) {
            showErrorAlert("Erro de Carregamento", "Não foi possível carregar os agendamentos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleFilterByDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            loadAppointments(selectedDate);
        } else {
        }
    }

    private void handleFilterToday(ActionEvent event) {
        loadAppointments(LocalDate.now());
    }

    private void handleFilterThisWeek(ActionEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<Agendamento> allAppointments = clinica.listarTodosAgendamentos();
        if (allAppointments != null) {
            ObservableList<AgendamentoRespostaDTO> weeklyAppointments = FXCollections.observableArrayList(
                    allAppointments.stream()
                            .filter(app -> app.getDataAgendamento().toLocalDate().isAfter(startOfWeek.minusDays(1)) &&
                                    app.getDataAgendamento().toLocalDate().isBefore(endOfWeek.plusDays(1)))
                            .map(Agendamento::paraDTO)
                            .collect(Collectors.toList())
            );
            appointmentTable.setItems(weeklyAppointments);
        }
    }

    private void handleFilterAll(ActionEvent event) {
        loadAppointments(null);
    }

    private void displayAppointmentDetails(AgendamentoRespostaDTO appointment) {
        idLabel.setText(String.valueOf(appointment.id()));
        dataHoraLabel.setText(appointment.dataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        clienteLabel.setText(appointment.nomeCliente());
        animalLabel.setText(appointment.nomeAnimal());
        veterinarioLabel.setText(appointment.nomeVeterinario());
        tipoLabel.setText("Funcionalidade Inacabada");
        statusLabel.setText(appointment.status().name());
        observacoesTextArea.setText(appointment.observacao());

        // Habilitar botões de ação se um agendamento for selecionado
        editarAgendamentoButton.setDisable(false);
        cancelarAgendamentoButton.setDisable(false);
        confirmarAgendamentoButton.setDisable(false);
        reagendarButton.setDisable(false);
    }

    private void clearAppointmentDetails() {
        idLabel.setText("-");
        dataHoraLabel.setText("-");
        clienteLabel.setText("-");
        animalLabel.setText("-");
        veterinarioLabel.setText("-");
        tipoLabel.setText("-");
        statusLabel.setText("-");
        observacoesTextArea.setText("");

        // Desabilitar botões de ação
        editarAgendamentoButton.setDisable(true);
        cancelarAgendamentoButton.setDisable(true);
        confirmarAgendamentoButton.setDisable(true);
        reagendarButton.setDisable(true);
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("atendente_tela.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Menu Principal");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível retornar à tela de atendente.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNovoAgendamento(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("novo_agendamento.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema Clínica Veterinária - Novo Agendamento");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro ao carregar tela", "Não foi possível carregar a tela de novo agendamento.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditarAgendamento(ActionEvent event) {
        AgendamentoRespostaDTO selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            showErrorAlert("Funcionalidade em desenvolvimento", "Lógica para editar agendamento (ID: " + selectedAppointment.id() + ")");
        }
    }

    @FXML
    private void handleCancelarAgendamento(ActionEvent event) {
        AgendamentoRespostaDTO selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            showErrorAlert("Funcionalidade em desenvolvimento", "Lógica para cancelar agendamento (ID: " + selectedAppointment.id() + ")");
        }
    }

    @FXML
    private void handleConfirmarAgendamento(ActionEvent event) {
        AgendamentoRespostaDTO selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            showErrorAlert("Funcionalidade em desenvolvimento", "Lógica para confirmar agendamento (ID: " + selectedAppointment.id() + ")");
        }
    }

    @FXML
    private void handleReagendar(ActionEvent event) {
        AgendamentoRespostaDTO selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            showErrorAlert("Funcionalidade em desenvolvimento", "Lógica para reagendar (ID: " + selectedAppointment.id() + ")");
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
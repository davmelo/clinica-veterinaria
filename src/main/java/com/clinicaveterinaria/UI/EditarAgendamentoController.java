package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AgendamentoRequisicaoDTO;
import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;
import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;
import com.clinicaveterinaria.negocio.entidades.procedimento.ProcedimentoTipo;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditarAgendamentoController {
    @FXML
    private Label lblAgendamentoId;

    @FXML
    private TextField txtClienteSearch;

    @FXML
    private Button btnBuscarCliente;

    @FXML
    private TextField txtClienteNome;

    @FXML
    private TextField txtClienteTelefone;

    @FXML
    private TextField txtClienteEmail;

    @FXML
    private ComboBox<String> cmbAnimal;

    @FXML
    private TextField txtAnimalNome;

    @FXML
    private TextField txtAnimalEspecie;

    @FXML
    private TextField txtAnimalRaca;

    @FXML
    private TextField txtAnimalIdade;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cmbHorario;

    @FXML
    private ComboBox<String> cmbVeterinario;

    @FXML
    private ComboBox<String> cmbTipoProcedimento;

    @FXML
    private TextArea txtObservacoes;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbPrioridade;

    @FXML
    private Label statusMessageLabel;

    @FXML
    private Button cancelarEdicaoButton;

    @FXML
    private Button salvarEdicaoButton;

    @FXML
    private Button voltarButton;


    private ServidorClinica clinica = ServidorClinica.getInstance();
    private Long agendamentoIdParaEdicao;
    private Agendamento agendamentoOriginal;

    private ClienteRespostaDTO clienteAtualmenteExibido;
    private List<Animal> animaisDoClienteAtualmenteExibido;
    private Animal animalAtualmenteExibido;
    private List<Veterinario> todosVeterinariosCarregados;
    private Veterinario veterinarioSelecionadoNoCombo;

    public void setAgendamentoId(Long id) {
        this.agendamentoIdParaEdicao = id;
        carregarAgendamentoParaEdicao();
    }

    @FXML
    public void initialize() {
        cmbHorario.setItems(FXCollections.observableArrayList(getHorasDoDia()));
        cmbTipoProcedimento.setItems(FXCollections.observableArrayList(
                Arrays.stream(ProcedimentoTipo.values()).map(Enum::name).collect(Collectors.toList())
        ));
        cmbStatus.setItems(FXCollections.observableArrayList(
                Arrays.stream(AgendamentoStatus.values()).map(Enum::name).collect(Collectors.toList())
        ));
        cmbPrioridade.setItems(FXCollections.observableArrayList("NORMAL", "URGENTE"));

        datePicker.setOnAction(this::handleDataSelecionada);
        cmbVeterinario.setOnAction(this::handleVeterinarioSelecionado);
        btnBuscarCliente.setOnAction(this::handleBuscarCliente);
        cmbAnimal.setOnAction(this::handleAnimalSelecionado);
        cmbHorario.setOnAction(this::handleHorarioSelecionado);
        cmbTipoProcedimento.setOnAction(this::handleTipoProcedimentoSelecionado);
        cmbStatus.setOnAction(this::handleStatusSelecionado);
        cmbPrioridade.setOnAction(this::handlePrioridadeSelecionada);

        carregarTodosVeterinariosParaComboBox();
    }

    private ObservableList<String> getHorasDoDia() {
        ObservableList<String> horas = FXCollections.observableArrayList();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 30) {
                horas.add(String.format("%02d:%02d", h, m));
            }
        }
        return horas;
    }

    private void carregarAgendamentoParaEdicao() {
        if (agendamentoIdParaEdicao == null) {
            showStatusMessage("Erro: ID do agendamento não fornecido para edição.", true);
            return;
        }
        try {
            agendamentoOriginal = clinica.buscarAgendamentoEntidade(agendamentoIdParaEdicao);
            if (agendamentoOriginal != null) {
                lblAgendamentoId.setText(String.valueOf(agendamentoOriginal.getId()));
                txtClienteSearch.setText(agendamentoOriginal.getCliente().getCpf());
                handleBuscarCliente(null); // Completar cliente e animal

                datePicker.setValue(agendamentoOriginal.getDataAgendamento().toLocalDate());
                cmbHorario.getSelectionModel().select(agendamentoOriginal.getDataAgendamento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                cmbVeterinario.getSelectionModel().select(agendamentoOriginal.getVeterinario().getNome());
                handleVeterinarioSelecionado(null);
                cmbTipoProcedimento.getSelectionModel().select(agendamentoOriginal.getTipoProcedimentoAgendado());
                txtObservacoes.setText(agendamentoOriginal.getObsevacao());
                cmbStatus.getSelectionModel().select(agendamentoOriginal.getStatus().name());
                // cmbPrioridade.getSelectionModel().select(agendamentoOriginal.getPrioridade());

                showStatusMessage("Agendamento " + agendamentoIdParaEdicao + " carregado para edição.", false);
            } else {
                showStatusMessage("Agendamento com ID " + agendamentoIdParaEdicao + " não encontrado.", true);
            }
        } catch (Exception e) {
            showStatusMessage("Erro ao carregar agendamento para edição: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void carregarTodosVeterinariosParaComboBox() {
        todosVeterinariosCarregados = clinica.listarTodosVeterinarios();
        if (todosVeterinariosCarregados != null && !todosVeterinariosCarregados.isEmpty()) {
            cmbVeterinario.setItems(FXCollections.observableArrayList(
                    todosVeterinariosCarregados.stream().map(Veterinario::getNome).collect(Collectors.toList())
            ));
        } else {
            showStatusMessage("Nenhum veterinário disponível.", true);
        }
    }

    @FXML
    private void handleDataSelecionada(ActionEvent event) {
        atualizarHorariosDisponiveis();
    }

    @FXML
    private void handleHorarioSelecionado(ActionEvent event) {
        // Lógica específica para quando o horário é selecionado
    }

    @FXML
    private void handleVeterinarioSelecionado(ActionEvent event) {
        String nomeVeterinarioSelecionado = cmbVeterinario.getSelectionModel().getSelectedItem();
        if (nomeVeterinarioSelecionado != null && todosVeterinariosCarregados != null) {
            veterinarioSelecionadoNoCombo = todosVeterinariosCarregados.stream()
                    .filter(v -> v.getNome().equals(nomeVeterinarioSelecionado))
                    .findFirst()
                    .orElse(null);
            atualizarHorariosDisponiveis();
        }
    }

    @FXML
    private void handleTipoProcedimentoSelecionado(ActionEvent event) {
        // Lógica para lidar com a seleção do tipo de procedimento
    }

    @FXML
    private void handleStatusSelecionado(ActionEvent event) {
        // Lógica para lidar com a seleção do status
    }

    @FXML
    private void handlePrioridadeSelecionada(ActionEvent event) {
        // Lógica para lidar com a seleção da prioridade
    }

    private void atualizarHorariosDisponiveis() {
        if (veterinarioSelecionadoNoCombo == null || datePicker.getValue() == null) {
            cmbHorario.setItems(FXCollections.emptyObservableList());
            showStatusMessage("Selecione o veterinário e a data para ver os horários disponíveis.", false);
            return;
        }

        LocalDate dataAgendamento = datePicker.getValue();
        DayOfWeek diaDaSemana = dataAgendamento.getDayOfWeek();
        DiaSemana diaSemanaEnum = DiaSemana.valueOf(diaDaSemana.name());

        DisponibilidadeAgenda disponibilidade = clinica.getDisponibilidadeVeterinario(veterinarioSelecionadoNoCombo.getCrmv());

        if (disponibilidade != null) {
            List<LocalTime> horariosDoDia = disponibilidade.getHorariosPorDia(diaSemanaEnum);
            horariosDoDia.sort(LocalTime::compareTo);

            cmbHorario.setItems(FXCollections.observableArrayList(
                    horariosDoDia.stream().map(LocalTime::toString).collect(Collectors.toList())
            ));
            if (horariosDoDia.isEmpty()) {
                showStatusMessage("Não há horários disponíveis para este veterinário nesta data.", true);
            } else {
                showStatusMessage("", false);
            }
        } else {
            cmbHorario.setItems(FXCollections.emptyObservableList());
            showStatusMessage("Disponibilidade não encontrada para este veterinário.", true);
        }
    }

    @FXML
    private void handleBuscarCliente(ActionEvent event) {
        String termoBusca = txtClienteSearch.getText().trim();
        if (termoBusca.isEmpty()) {
            showStatusMessage("Digite o CPF do cliente para buscar.", true);
            limparCamposClienteEAnimal();
            return;
        }
        if (!termoBusca.matches("\\d{11}")) {
            showStatusMessage("Por favor, digite um CPF válido (11 dígitos numéricos).", true);
            limparCamposClienteEAnimal();
            return;
        }
        try {
            ClienteRespostaDTO clienteEncontradoDTO = clinica.buscarCliente(termoBusca);
            if (clienteEncontradoDTO != null) {
                clienteAtualmenteExibido = clienteEncontradoDTO;
                preencherCamposCliente(clienteEncontradoDTO);
                carregarAnimaisDoCliente(clienteEncontradoDTO.cpf());
                showStatusMessage("Cliente encontrado: " + clienteEncontradoDTO.nome() + " " + clienteEncontradoDTO.sobrenome(), false);
            } else {
                showStatusMessage("Cliente não encontrado.", true);
                limparCamposClienteEAnimal();
            }
        } catch (Exception e) {
            showStatusMessage("Erro ao buscar cliente: " + e.getMessage(), true);
            e.printStackTrace();
            limparCamposClienteEAnimal();
        }
    }

    private void preencherCamposCliente(ClienteRespostaDTO clienteDTO) {
        txtClienteNome.setText(clienteDTO.nome() + " " + clienteDTO.sobrenome());
        txtClienteTelefone.setText(clienteDTO.telefone());
        txtClienteEmail.setText(clienteDTO.email());
    }

    private void limparCamposClienteEAnimal() {
        txtClienteNome.clear();
        txtClienteTelefone.clear();
        txtClienteEmail.clear();
        cmbAnimal.setItems(FXCollections.emptyObservableList());
        limparCamposAnimal();
        clienteAtualmenteExibido = null;
        animaisDoClienteAtualmenteExibido = null;
        animalAtualmenteExibido = null;
    }

    private void carregarAnimaisDoCliente(String clienteCpf) {
        List<Animal> animais = clinica.listarTodosAnimaisPorTutorCpf(clienteCpf);
        if (animais != null && !animais.isEmpty()) {
            animaisDoClienteAtualmenteExibido = animais;
            cmbAnimal.setItems(FXCollections.observableArrayList(
                    animais.stream().map(Animal::getNome).collect(Collectors.toList())
            ));
            if (agendamentoOriginal != null && agendamentoOriginal.getAnimal() != null) {
                cmbAnimal.getSelectionModel().select(agendamentoOriginal.getAnimal().getNome());
            } else {
                cmbAnimal.getSelectionModel().selectFirst();
            }
            handleAnimalSelecionado(null);
        } else {
            cmbAnimal.setItems(FXCollections.emptyObservableList());
            limparCamposAnimal();
            showStatusMessage("Nenhum animal encontrado para este cliente.", true);
        }
    }

    @FXML
    private void handleAnimalSelecionado(ActionEvent event) {
        String nomeAnimalSelecionado = cmbAnimal.getSelectionModel().getSelectedItem();
        if (nomeAnimalSelecionado != null && animaisDoClienteAtualmenteExibido != null) {
            animalAtualmenteExibido = animaisDoClienteAtualmenteExibido.stream()
                    .filter(a -> a.getNome().equals(nomeAnimalSelecionado))
                    .findFirst()
                    .orElse(null);

            if (animalAtualmenteExibido != null) {
                preencherCamposAnimal(animalAtualmenteExibido);
            } else {
                limparCamposAnimal();
            }
        }
    }

    private void preencherCamposAnimal(Animal animal) {
        txtAnimalNome.setText(animal.getNome());
        txtAnimalEspecie.setText(animal.getEspecie());
        txtAnimalRaca.setText(animal.getRaca());
        txtAnimalIdade.setText(String.valueOf(animal.getIdade()));
    }

    private void limparCamposAnimal() {
        txtAnimalNome.clear();
        txtAnimalEspecie.clear();
        txtAnimalRaca.clear();
        txtAnimalIdade.clear();
        animalAtualmenteExibido = null;
    }

    @FXML
    private void handleSalvarEdicao(ActionEvent event) {
        showStatusMessage("", false);

        LocalDate dataSelecionada = datePicker.getValue();
        String horaSelecionadaStr = cmbHorario.getSelectionModel().getSelectedItem();
        String veterinarioNome = cmbVeterinario.getSelectionModel().getSelectedItem();
        String tipoProcedimentoSelecionado = cmbTipoProcedimento.getSelectionModel().getSelectedItem();
        String observacao = txtObservacoes.getText();
        String statusSelecionado = cmbStatus.getSelectionModel().getSelectedItem();
        String prioridadeSelecionada = cmbPrioridade.getSelectionModel().getSelectedItem();


        if (clienteAtualmenteExibido == null || animalAtualmenteExibido == null ||
                veterinarioNome == null || dataSelecionada == null || horaSelecionadaStr == null ||
                tipoProcedimentoSelecionado == null) {
            showStatusMessage("Por favor, preencha todos os campos obrigatórios.", true);
            return;
        }

        Veterinario veterinarioSelecionado = todosVeterinariosCarregados.stream()
                .filter(v -> v.getNome().equals(veterinarioNome))
                .findFirst()
                .orElse(null);

        if (veterinarioSelecionado == null) {
            showStatusMessage("Erro: Veterinário selecionado não encontrado nos dados.", true);
            return;
        }

        LocalTime horaSelecionada = LocalTime.parse(horaSelecionadaStr);
        LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataSelecionada, horaSelecionada);

        AgendamentoStatus statusEnum = AgendamentoStatus.valueOf(statusSelecionado.toUpperCase());


        AgendamentoRequisicaoDTO agendamentoDTO = new AgendamentoRequisicaoDTO(
                agendamentoIdParaEdicao,
                clienteAtualmenteExibido.cpf(),
                animalAtualmenteExibido.getId(),
                veterinarioSelecionado.getCrmv(),
                dataHoraAgendamento,
                observacao,
                statusEnum,
                tipoProcedimentoSelecionado
        );

        try {
            clinica.atualizarAgendamento(agendamentoDTO.id(), agendamentoDTO);
            showStatusMessage("Agendamento atualizado com sucesso!", false);
            navegarParaGestaoAgendamentos();
        } catch (Exception e) {
            showStatusMessage("Ocorreu um erro ao salvar edição: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void navegarParaGestaoAgendamentos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agendamentos_gestao.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) salvarEdicaoButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestão de Agendamentos");
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erro de Navegação", "Não foi possível retornar à tela de gestão de agendamentos.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleVoltar(ActionEvent event) {
        navegarParaGestaoAgendamentos();
    }

    @FXML
    public void handleCancelarEdicao(ActionEvent event) {
        navegarParaGestaoAgendamentos();
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
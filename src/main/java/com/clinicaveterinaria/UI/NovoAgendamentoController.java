package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AgendamentoRequisicaoDTO;
import com.clinicaveterinaria.dtos.AnimalRespostaDTO;
import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.*;

import com.clinicaveterinaria.negocio.entidades.procedimento.ProcedimentoTipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NovoAgendamentoController {

    // Seções do Cliente
    @FXML
    private TextField txtClienteSearch;

    @FXML
    private Button btnBuscarCliente;

    @FXML
    private Button btnNovoCliente;

    @FXML
    private TextField txtClienteNome;

    @FXML
    private TextField txtClienteTelefone;

    @FXML
    private TextField txtClienteEmail;

    // Seções do Animal
    @FXML
    private ComboBox<String> cmbAnimal; // Vão aparecer os nomes, mas o objeto Animal será guardado em 'animaisDoClienteSelecionado'
    @FXML
    private Button btnNovoAnimal;
    @FXML
    private TextField txtAnimalNome;
    @FXML
    private TextField txtAnimalEspecie;
    @FXML
    private TextField txtAnimalRaca;
    @FXML
    private TextField txtAnimalIdade;

    // Detalhes do Agendamento
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cmbHorario;

    @FXML
    private ComboBox<String> cmbVeterinario;// Vão aparecer o nome, mas o objeto Veterinario será guardado em 'todosVeterinarios'

    @FXML
    private ComboBox<String> cmbTipoProcedimento; // Vão aparecer os nomes, mas o objeto ProcedimentoTipo será usado

    @FXML
    private TextArea txtObservacoes;

    // Seções de Status
    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbPrioridade;

    @FXML
    private Label statusMessageLabel;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    private ServidorClinica clinica = ServidorClinica.getInstance();

    @Setter
    private String perfilUsuario;

    // Listas para armazenar os objetos reais, para obter seus IDs/CPFs/CRMVs
    //private List<Cliente> todosClientesCarregados; // Para buscar o cliente pelo nome exibido
    private ClienteRespostaDTO clienteAtualmenteExibido; // O cliente encontrado/selecionado
    private List<Animal> animaisDoClienteAtualmenteExibido; // Animais do cliente exibido
    private Animal animalAtualmenteExibido; // O animal selecionado no ComboBox
    private List<Veterinario> todosVeterinariosCarregados; // Para buscar o veterinário pelo nome exibido
    private Veterinario veterinarioSelecionadoNoCombo;

    @FXML
    public void initialize() {
        // Inicializar ComboBox de Horários (ex: a cada 30 minutos)
        ObservableList<String> horas = FXCollections.observableArrayList();
        for (int h = 8; h <= 18; h++) { // Exemplo: das 8h às 18h
            for (int m = 0; m < 60; m += 30) {
                horas.add(String.format("%02d:%02d", h, m));
            }
        }
        cmbHorario.setItems(horas);

        // Preencher ComboBox de Status (se necessário, ou deixe PENDENTE como padrão)
        cmbStatus.setItems(FXCollections.observableArrayList(
                AgendamentoStatus.PENDENTE.name(),
                AgendamentoStatus.AGENDADO.name()
        ));
        cmbStatus.getSelectionModel().select(AgendamentoStatus.PENDENTE.name()); // Define PENDENTE como padrão

        // Preencher ComboBox de Prioridade
        cmbPrioridade.setItems(FXCollections.observableArrayList("Normal", "Urgente"));
        cmbPrioridade.getSelectionModel().select("Normal"); // Define Normal como padrão

        // Preencher ComboBox de Tipo de Procedimento
        cmbTipoProcedimento.setItems(FXCollections.observableArrayList(
                ProcedimentoTipo.NENHUM.name(),
                ProcedimentoTipo.CIRURGIA.name(),
                ProcedimentoTipo.VACINA.name(),
                ProcedimentoTipo.EXAME.name()

        ));
        cmbTipoProcedimento.getSelectionModel().select(ProcedimentoTipo.NENHUM.name()); // Define um padrão

        carregarTodosVeterinariosParaComboBox(); // Carrega os veterinários na inicialização
    }

    @FXML
    private void handleBuscarCliente(ActionEvent event) {
        String termoBusca = txtClienteSearch.getText().trim();
        if (termoBusca.isEmpty()) {
            showStatusMessage("Por favor, digite o CPF do cliente para buscar.", true);
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

    private void preencherCamposCliente(ClienteRespostaDTO cliente) {
        txtClienteNome.setText(cliente.nome() + " " + cliente.sobrenome());
        txtClienteTelefone.setText(cliente.telefone());
        txtClienteEmail.setText(cliente.email());
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

    @FXML
    private void handleNovoCliente(ActionEvent event) {
        showErrorAlert("Funcionalidade em desenvolvimento", "Abrir tela de cadastro de novo cliente.");
    }

    private void carregarAnimaisDoCliente(String clienteCpf) {
        List<Animal> animais = clinica.listarTodosAnimaisPorTutorCpf(clienteCpf);

        if (animais != null && !animais.isEmpty()) {
            animaisDoClienteAtualmenteExibido = animais;
            cmbAnimal.setItems(FXCollections.observableArrayList(
                    animais.stream().map(Animal::getNome).collect(Collectors.toList())
            ));
            cmbAnimal.getSelectionModel().selectFirst();
            handleAnimalSelected();
        } else {
            cmbAnimal.setItems(FXCollections.emptyObservableList());
            limparCamposAnimal();
            showStatusMessage("Nenhum animal encontrado para este cliente.", true);
        }
    }

    @FXML
    private void handleAnimalSelected() {
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
        txtAnimalIdade.setText(String.valueOf(animal.getIdade())); // Idade é um int
    }

    private void limparCamposAnimal() {
        txtAnimalNome.clear();
        txtAnimalEspecie.clear();
        txtAnimalRaca.clear();
        txtAnimalIdade.clear();
        animalAtualmenteExibido = null;
    }

    @FXML
    private void handleNovoAnimal(ActionEvent event) {
        showErrorAlert("Funcionalidade em desenvolvimento", "Abrir tela de cadastro de novo animal.");
    }

    private void carregarTodosVeterinariosParaComboBox() {
        todosVeterinariosCarregados = clinica.listarTodosVeterinarios();

        if (todosVeterinariosCarregados != null && !todosVeterinariosCarregados.isEmpty()) {
            cmbVeterinario.setItems(FXCollections.observableArrayList(
                    todosVeterinariosCarregados.stream().map(Veterinario::getNome).collect(Collectors.toList())
            ));
        } else {
            cmbVeterinario.setItems(FXCollections.emptyObservableList());
            showStatusMessage("Nenhum veterinário cadastrado.", true);
        }
    }

    @FXML
    private void handleDateSelected() {
        atualizarHorariosDisponiveis();
    }

    @FXML
    private void handleHorarioSelected() {
        // Lógica específica para quando o horário é selecionado, se houver.
    }

    @FXML
    private void handleVeterinarioSelected() {
        String nomeVeterinarioSelecionado = cmbVeterinario.getSelectionModel().getSelectedItem();
        if (nomeVeterinarioSelecionado != null && todosVeterinariosCarregados != null) {
            veterinarioSelecionadoNoCombo = todosVeterinariosCarregados.stream()
                    .filter(v -> v.getNome().equals(nomeVeterinarioSelecionado))
                    .findFirst()
                    .orElse(null);
            atualizarHorariosDisponiveis(); // Atualiza os horários sempre que o veterinário muda
        }
    }

    @FXML
    private void handleTipoProcedimentoSelected() {
        // Lógica para lidar com a seleção do tipo de procedimento.
    }


    @FXML
    private void handleStatusSelected() {
    }

    @FXML
    private void handlePrioridadeSelected() {
    }

    private void atualizarHorariosDisponiveis() {
        if (veterinarioSelecionadoNoCombo == null || datePicker.getValue() == null) {
            cmbHorario.setItems(FXCollections.emptyObservableList());
            showStatusMessage("Selecione o veterinário e a data para ver os horários disponíveis.", false);
            return;
        }

        LocalDate dataAgendamento = datePicker.getValue();
        // Converte DayOfWeek do LocalDate para DiaSemana do seu enum
        DiaSemana diaDaSemana = DiaSemana.valueOf(dataAgendamento.getDayOfWeek().name());

        // Obter a disponibilidade do veterinário
        DisponibilidadeAgenda disponibilidade = clinica.getDisponibilidadeVeterinario(veterinarioSelecionadoNoCombo.getCrmv());

        if (disponibilidade != null) {
            List<LocalTime> horariosDoDia = disponibilidade.getHorariosPorDia(diaDaSemana);
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
    private void handleAgendar(ActionEvent event) {
        showStatusMessage("", false);

        LocalDate dataSelecionada = datePicker.getValue();
        String horaSelecionadaStr = cmbHorario.getSelectionModel().getSelectedItem();
        String veterinarioNome = cmbVeterinario.getSelectionModel().getSelectedItem();
        String observacao = txtObservacoes.getText();
        String statusSelecionado = cmbStatus.getSelectionModel().getSelectedItem();
        String prioridadeSelecionada = cmbPrioridade.getSelectionModel().getSelectedItem();
        String tipoProcedimentoSelecionado = cmbTipoProcedimento.getSelectionModel().getSelectedItem();

        if (clienteAtualmenteExibido == null || animalAtualmenteExibido == null ||
                veterinarioNome == null || dataSelecionada == null || horaSelecionadaStr == null) {
            showStatusMessage("Por favor, selecione o Cliente, Animal, Veterinário, Data e Horário.", true);
            return;
        }

//        Veterinario veterinarioSelecionado = todosVeterinariosCarregados.stream()
//                .filter(v -> v.getNome().equals(veterinarioNome))
//                .findFirst()
//                .orElse(null);
//
//        if (veterinarioSelecionado == null) {
//            showStatusMessage("Erro: Veterinário selecionado não encontrado nos dados.", true);
//            return;
//        }

        LocalTime horaSelecionada = LocalTime.parse(horaSelecionadaStr);
        LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataSelecionada, horaSelecionada);

        AgendamentoStatus statusEnum = AgendamentoStatus.valueOf(statusSelecionado.toUpperCase());

        AgendamentoRequisicaoDTO agendamentoDTO = new AgendamentoRequisicaoDTO(
                null,
                clienteAtualmenteExibido.cpf(),
                animalAtualmenteExibido.getId(),
                veterinarioSelecionadoNoCombo.getCrmv(),
                dataHoraAgendamento,
                observacao,
                statusEnum,
                tipoProcedimentoSelecionado
        );

        try {
            boolean sucesso = clinica.cadastrarAgendamento(agendamentoDTO);
            if (sucesso) {
                showStatusMessage("Agendamento realizado com sucesso!", false);
                limparTodosCamposDoFormulario();
            } else {
                showStatusMessage("Não foi possível agendar. Horário indisponível ou dados inválidos.", true);
            }
        } catch (Exception e) {
            showStatusMessage("Ocorreu um erro ao agendar: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void limparTodosCamposDoFormulario() {
        txtClienteSearch.clear();
        limparCamposClienteEAnimal();
        cmbVeterinario.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        cmbHorario.getSelectionModel().clearSelection();
        txtObservacoes.clear();
        cmbStatus.getSelectionModel().select(AgendamentoStatus.PENDENTE.name());
        cmbPrioridade.getSelectionModel().select("Normal");
    }

    private void showStatusMessage(String message, boolean isError) {
        statusMessageLabel.setText(message);
        statusMessageLabel.setTextFill(isError ? Color.RED : Color.GREEN);
    }

    @FXML
    public void handleCancelar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agendamentos_gestao.fxml"));
            Parent root = loader.load();

            AgendamentosController controller = loader.getController();
            controller.configurarPerfil(perfilUsuario);

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

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

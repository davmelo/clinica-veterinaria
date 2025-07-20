package com.clinicaveterinaria.UI;

import com.clinicaveterinaria.dtos.AtendimentoRequisicaoDTO;
import com.clinicaveterinaria.dtos.ProcedimentoRealizadoRequisicaoDTO;
import com.clinicaveterinaria.negocio.ServidorClinica;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import com.clinicaveterinaria.negocio.entidades.procedimento.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AtendimentoController {

    @FXML
    private Label lblAgendamentoId;

    @FXML
    private Label lblDataHoraAgendada;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblAnimal;

    @FXML
    private Label lblVeterinarioAgendado;

    @FXML
    private Label lblStatusAgendamento;

    @FXML
    private TextArea txtObservacaoAgendamento;

    //Atendimento
    @FXML
    private DatePicker dpDataAtendimento;

    @FXML
    private TextArea txtObservacoesAtendimento;

    // Procedimentos Realizados
    @FXML
    private ComboBox<String> cmbTipoProcedimento;

    @FXML
    private TextField txtNomeProcedimento;

    @FXML
    private ComboBox<String> cmbVeterinarioProcedimento;

    @FXML
    private Button handleAddProcedimentoButton;

    // Detalhes do Procedimento
    @FXML
    private VBox procedimentoDetailsBox;

    @FXML
    private GridPane cirurgiaGrid;

    @FXML
    private TextField txtParteCorpo;

    @FXML
    private CheckBox chkInternacao;

    @FXML
    private GridPane exameGrid;

    @FXML
    private TextField txtResultadoExame;

    @FXML
    private GridPane vacinaGrid;

    @FXML
    private TextField txtNumeroDoses;

    @FXML
    private TextField txtNumeroAplicacao;

    @FXML
    private ComboBox<String> cmbTipoAplicacaoVacina;

    @FXML
    private DatePicker dpValidadeVacina;

    @FXML
    private ListView<String> procedimentosListView;

    @FXML
    private Button handleRemoverProcedimentoButton;

    // Concluir atendimento e voltar
    @FXML private Label statusMessageLabel;

    @FXML private Button handleConcluirAtendimentoButton;

    @FXML private Button voltarButton;

    private ServidorClinica clinica = ServidorClinica.getInstance();
    private Long agendamentoId;
    private Agendamento agendamentoOriginal;
    private List<ProcedimentoRealizadoRequisicaoDTO> procedimentosRealizadosList = new ArrayList<>();

    private List<Veterinario> todosVeterinariosCarregados;

    public void setAgendamentoId(Long id) {
        this.agendamentoId = id;
        carregarDetalhesAgendamento();
        carregarVeterinariosProcedimento();
        dpDataAtendimento.setValue(LocalDate.now());
    }

    @FXML
    public void initialize() {
        cmbTipoProcedimento.setItems(FXCollections.observableArrayList(
                Arrays.stream(ProcedimentoTipo.values()).map(Enum::name).collect(Collectors.toList())
        ));
        cmbTipoProcedimento.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            toggleProcedimentoDetails(newVal);
        });
        cmbTipoProcedimento.getSelectionModel().selectFirst();

        cmbTipoAplicacaoVacina.setItems(FXCollections.observableArrayList(
                Arrays.stream(VacinaTipoAplicacao.values()).map(Enum::name).collect(Collectors.toList())
        ));
        cmbTipoAplicacaoVacina.getSelectionModel().selectFirst();
    }

    private void carregarDetalhesAgendamento() {
        if (agendamentoId == null) {
            showStatusMessage("Erro: ID do agendamento não fornecido para atendimento.", true);
            return;
        }
        try {
            agendamentoOriginal = clinica.buscarAgendamentoEntidade(agendamentoId);
            if (agendamentoOriginal != null) {
                lblAgendamentoId.setText(String.valueOf(agendamentoOriginal.getId()));
                lblDataHoraAgendada.setText(agendamentoOriginal.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                lblCliente.setText(agendamentoOriginal.getCliente().getNome() + " " + agendamentoOriginal.getCliente().getSobrenome());
                lblAnimal.setText(agendamentoOriginal.getAnimal().getNome() + " (" + agendamentoOriginal.getAnimal().getEspecie() + ")");
                lblVeterinarioAgendado.setText(agendamentoOriginal.getVeterinario().getNome() + " " + agendamentoOriginal.getVeterinario().getSobrenome());
                lblStatusAgendamento.setText(agendamentoOriginal.getStatus().name());
                txtObservacaoAgendamento.setText(agendamentoOriginal.getObsevacao());
                showStatusMessage("Agendamento " + agendamentoId + " carregado.", false);
            } else {
                showStatusMessage("Agendamento com ID " + agendamentoId + " não encontrado.", true);
            }
        } catch (Exception e) {
            showStatusMessage("Erro ao carregar detalhes do agendamento: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private void carregarVeterinariosProcedimento() {
        todosVeterinariosCarregados = clinica.listarTodosVeterinarios();
        if (todosVeterinariosCarregados != null && !todosVeterinariosCarregados.isEmpty()) {
            cmbVeterinarioProcedimento.setItems(FXCollections.observableArrayList(
                    todosVeterinariosCarregados.stream().map(Veterinario::getNome).collect(Collectors.toList())
            ));
        } else {
            showStatusMessage("Nenhum veterinário disponível para procedimentos.", true);
        }
    }

    private void toggleProcedimentoDetails(String tipo) {
        cirurgiaGrid.setVisible(false);
        cirurgiaGrid.setManaged(false);
        exameGrid.setVisible(false);
        exameGrid.setManaged(false);
        vacinaGrid.setVisible(false);
        vacinaGrid.setManaged(false);

        if (tipo != null) {
            ProcedimentoTipo procTipo = ProcedimentoTipo.valueOf(tipo.toUpperCase());
            switch (procTipo) {
                case CIRURGIA:
                    cirurgiaGrid.setVisible(true);
                    cirurgiaGrid.setManaged(true);
                    break;
                case EXAME:
                    exameGrid.setVisible(true);
                    exameGrid.setManaged(true);
                    break;
                case VACINA:
                    vacinaGrid.setVisible(true);
                    vacinaGrid.setManaged(true);
                    break;
                default:
                    break;
            }
        }
        txtParteCorpo.clear();
        chkInternacao.setSelected(false);
        txtResultadoExame.clear();
        txtNumeroDoses.clear();
        txtNumeroAplicacao.clear();
        cmbTipoAplicacaoVacina.getSelectionModel().clearSelection();
        dpValidadeVacina.setValue(null);
    }

    @FXML
    private void handleAddProcedimento(ActionEvent event) {
        String tipoProcStr = cmbTipoProcedimento.getSelectionModel().getSelectedItem();
        String nomeProc = txtNomeProcedimento.getText().trim();
        String vetProcNome = cmbVeterinarioProcedimento.getSelectionModel().getSelectedItem();

        if (tipoProcStr == null || nomeProc.isEmpty() || vetProcNome == null) {
            showStatusMessage("Preencha Tipo, Nome e Veterinário para o procedimento.", true);
            return;
        }
        Veterinario vetQueRealizou = todosVeterinariosCarregados.stream()
                .filter(v -> v.getNome().equals(vetProcNome))
                .findFirst()
                .orElse(null);

        if (vetQueRealizou == null) {
            showStatusMessage("Veterinário do procedimento não encontrado.", true);
            return;
        }

        ProcedimentoTipo tipoProcedimento = ProcedimentoTipo.valueOf(tipoProcStr.toUpperCase());
        ProcedimentoRealizadoRequisicaoDTO procDTO = null;

        Long tempProcId = (long) (procedimentosRealizadosList.size() + 1);

        switch (tipoProcedimento) {
            case CIRURGIA:
                String parteCorpo = txtParteCorpo.getText().trim();
                boolean internacao = chkInternacao.isSelected();
                if (parteCorpo.isEmpty()) { showStatusMessage("Parte do corpo da cirurgia é obrigatória.", true); return; }
                procDTO = new ProcedimentoRealizadoRequisicaoDTO(
                        tempProcId, tipoProcedimento, nomeProc, vetQueRealizou.getCrmv(), agendamentoOriginal.getAnimal().getId(),
                        LocalDate.now(), null, null, parteCorpo, internacao, null, null, null, null, null);
                break;
            case EXAME:
                String resultadoExame = txtResultadoExame.getText().trim();
                if (resultadoExame.isEmpty()) { showStatusMessage("Resultado do exame é obrigatório.", true); return; }
                procDTO = new ProcedimentoRealizadoRequisicaoDTO(
                        tempProcId, tipoProcedimento, nomeProc, vetQueRealizou.getCrmv(), agendamentoOriginal.getAnimal().getId(),
                        LocalDate.now(), resultadoExame, null, null, null, null, null, null, null, null);
                break;
            case VACINA:
                String numDosesStr = txtNumeroDoses.getText().trim();
                String numAplicacaoStr = txtNumeroAplicacao.getText().trim();
                String tipoAplicacaoStr = cmbTipoAplicacaoVacina.getSelectionModel().getSelectedItem();
                LocalDate validade = dpValidadeVacina.getValue();
                if (numDosesStr.isEmpty() || numAplicacaoStr.isEmpty() || tipoAplicacaoStr == null || validade == null) {
                    showStatusMessage("Preencha todos os campos da vacina.", true); return; }
                try {
                    Integer numDoses = Integer.parseInt(numDosesStr);
                    Integer numAplicacao = Integer.parseInt(numAplicacaoStr);
                    procDTO = new ProcedimentoRealizadoRequisicaoDTO(
                            tempProcId, tipoProcedimento, nomeProc, vetQueRealizou.getCrmv(), agendamentoOriginal.getAnimal().getId(),
                            LocalDate.now(), null, null, null, null, null, numDoses, numAplicacao, tipoAplicacaoStr, validade);
                } catch (NumberFormatException e) { showStatusMessage("Número de doses/aplicação deve ser numérico.", true); return; }
                break;
            default:
                showStatusMessage("Tipo de procedimento não reconhecido.", true);
                return;
        }

        if (procDTO != null) {
            procedimentosRealizadosList.add(procDTO);
            procedimentosListView.setItems(FXCollections.observableArrayList(
                    procedimentosRealizadosList.stream().map(p -> p.tipoProcedimento().name() + ": " + p.nomeProcedimento() + " por " + p.veterinarioCrmv()).collect(Collectors.toList())
            ));
            showStatusMessage("Procedimento adicionado: " + nomeProc, false);
            cmbTipoProcedimento.getSelectionModel().selectFirst();
            txtNomeProcedimento.clear();
            cmbVeterinarioProcedimento.getSelectionModel().clearSelection();
            toggleProcedimentoDetails(null);
        }
    }

    @FXML
    private void handleRemoverProcedimento(ActionEvent event) {
        int selectedIndex = procedimentosListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            procedimentosRealizadosList.remove(selectedIndex);
            procedimentosListView.setItems(FXCollections.observableArrayList(
                    procedimentosRealizadosList.stream().map(p -> p.tipoProcedimento().name() + ": " + p.nomeProcedimento() + " por " + p.veterinarioCrmv()).collect(Collectors.toList())
            ));
            showStatusMessage("Procedimento removido.", false);
        } else {
            showStatusMessage("Selecione um procedimento para remover.", true);
        }
    }

    @FXML
    private void handleConcluirAtendimento(ActionEvent event) {
        if (agendamentoOriginal == null) {
            showStatusMessage("Erro: Nenhum agendamento carregado para concluir o atendimento.", true);
            return;
        }
        LocalDate dataAtendimento = dpDataAtendimento.getValue();
        if (dataAtendimento == null) {
            showStatusMessage("Por favor, selecione a data do atendimento.", true);
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar Atendimento");
        confirmAlert.setHeaderText("Concluir Atendimento para Agendamento ID: " + agendamentoOriginal.getId());
        confirmAlert.setContentText("Deseja realmente concluir este atendimento?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                AtendimentoRequisicaoDTO atendimentoDTO = new AtendimentoRequisicaoDTO(
                        null,
                        agendamentoOriginal.getId(),
                        LocalDateTime.of(dataAtendimento, LocalTime.now()),
                        procedimentosRealizadosList
                );

                clinica.cadastrarAtendimento(atendimentoDTO);

                agendamentoOriginal.setStatus(AgendamentoStatus.CONCLUIDO);

                showStatusMessage("Atendimento concluído com sucesso e agendamento marcado como CONCLUÍDO!", false);
                handleVoltar();
            } catch (Exception e) {
                showStatusMessage("Erro ao concluir atendimento: " + e.getMessage(), true);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agendamentos_gestao.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Gestão de Agendamentos");
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erro de Navegação", "Não foi possível retornar à tela de gestão de agendamentos.");
            e.printStackTrace();
        }
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
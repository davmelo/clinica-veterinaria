package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dtos.*;
import com.clinicaveterinaria.negocio.entidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServidorClinica {
    private static ServidorClinica instance;
    private final ControladorCliente controladorCliente;
    private final ControladorAnimal controladorAnimal;
    private final ControladorVeterinario controladorVeterinario;
    private final ControladorAgendamento controladorAgendamento;
    private final ControladorAtendimento controladorAtendimento;

    private ServidorClinica() {
        controladorCliente = ControladorCliente.getInstance();
        controladorAnimal = ControladorAnimal.getInstance();
        controladorVeterinario = ControladorVeterinario.getInstance();
        controladorAgendamento = ControladorAgendamento.getInstance();
        controladorAtendimento = ControladorAtendimento.getInstance();
    }

    public static ServidorClinica getInstance() {
        if (instance == null) {
            instance = new ServidorClinica();
        }
        return instance;
    }

    public void cadastrarCliente(ClienteRequisicaoDTO clienteDTO) {
        controladorCliente.cadastrarCliente(clienteDTO);
    }
    public ClienteRespostaDTO buscarCliente(String cpf) {
        Cliente cliente = controladorCliente.buscarClientePorCpf(cpf);
        return cliente != null ? cliente.paraDTO() : null;
    }
    public void atualizarCliente(ClienteRequisicaoDTO clienteDTO){
        controladorCliente.atualizarCliente(clienteDTO);
    }
    public void removerCliente(String cpf){
        controladorCliente.removerCliente(cpf);
    }

    public void cadastrarAnimal(AnimalRequisicaoDTO animalDTO) {
        controladorAnimal.cadastrarAnimal(animalDTO);
    }
    public AnimalRespostaDTO buscarAnimal(Long animalID) {
        Animal animal = controladorAnimal.buscarAnimalPorId(animalID);
        return animal != null ? animal.paraDTO() : null;
    }
    public void atualizarAnimal(Long animalID, AnimalRequisicaoDTO animalDTO) {
        controladorAnimal.atualizarAnimal(animalID, animalDTO);
    }
    public void removerAnimal(Long animalID) {
        controladorAnimal.removerAnimal(animalID);
    }

    public void cadastrarVeterinario(VeterinarioRequisicaoDTO veterinarioDTO) {
        controladorVeterinario.cadastrarVeterinario(veterinarioDTO);
    }

    public VeterinarioRespostaDTO buscarVeterinario(String crmv) {
        Veterinario veterinario = controladorVeterinario.buscarVeterinarioPorCrmv(crmv);
        return veterinario != null ? veterinario.paraDTO() : null;
    }

    public Veterinario buscarVeterinarioEntidade(String crmv) {
        return controladorVeterinario.buscarVeterinarioPorCrmv(crmv);
    }

    public void atualizarVeterinario(String crmv, VeterinarioRequisicaoDTO veterinarioDTO) {
        controladorVeterinario.atualizarVeterinario(crmv, veterinarioDTO);
    }
    public void removerVeterinario(String crmv) {
        controladorVeterinario.removerVeterinario(crmv);
    }
    public void adiconarDispoAgenda(String crmv, DispoAgendaRequisicaoDTO agendaDTO) {
        controladorVeterinario.adiconarDispoAgenda(crmv, agendaDTO);
    }

    public boolean cadastrarAgendamento(AgendamentoRequisicaoDTO agendamentoDTO) {
        return controladorAgendamento.cadastrarAgendamento(agendamentoDTO);
    }

    public AgendamentoRespostaDTO buscarAgendamento(Long agendamentoID) {
        Agendamento agendamento = controladorAgendamento.buscarAgendamentoPorId(agendamentoID);
        return agendamento != null ? agendamento.paraDTO() : null;
    }

    public Agendamento buscarAgendamentoEntidade(Long agendamentoID) {
        return controladorAgendamento.buscarAgendamentoPorId(agendamentoID);
    }

    public List<Agendamento> listarTodosAgendamentos() {
        return controladorAgendamento.listarTodosAgendamentos();
    }

    public List<Agendamento> buscarAgendamentosPorDia(LocalDate data) {
        return controladorAgendamento.buscarAgendamentoPorDia(data);
    }

    public void atualizarAgendamentoStatus(Long id, AgendamentoStatus status) {
        controladorAgendamento.atualizarStatus(id, status);
    }

    public void atualizarAgendamento(Long id, AgendamentoRequisicaoDTO agendamentoDTO) {
        controladorAgendamento.atualizarAgendamento(id, agendamentoDTO);
    }
    public void cancelarAgendamento(Long id, String motivo) {
        controladorAgendamento.cancelarAgendamento(id, motivo);
    }
    public void remarcarAgendamento(Long id, LocalDateTime novaData, String motivo) {
        controladorAgendamento.remarcarAgendamento(id, novaData, motivo);
    }
    public void removerAgendamento(Long id) {
        controladorAgendamento.removerAgendamento(id);
    }

    public void cadastrarAtendimento(AtendimentoRequisicaoDTO atendimentoRequisicaoDTO) {
        controladorAtendimento.cadastrarAtendimento(atendimentoRequisicaoDTO);
    }
    public AtendimentoRespostaDTO buscarAtendimento(Long atendimentoID) {
        Atendimento atendimento = controladorAtendimento.buscarAtendimento(atendimentoID);
        return atendimento != null ? atendimento.paraDTO() : null;
    }
    public void atualizarAtendimento(Long id, AtendimentoRequisicaoDTO atendimentoDTO) {
        controladorAtendimento.atualizarAtendimento(id, atendimentoDTO);
    }
    public void removerAtendimento(Long id) {
        controladorAtendimento.removerAtendimento(id);
    }

    public VeterinarioRespostaDTO autenticarVeterinario(String login, String senha) {
        Veterinario veterinario = controladorVeterinario.autenticar(login, senha);
        return veterinario != null ? veterinario.paraDTO() : null;
    }
    public List<Agendamento> listarAgendamentosPorVeterinario(String crmv) {
        return controladorAgendamento.buscarPorVeterinario(crmv);
    }

    public List<Cliente> listarTodosClientes() {
        return controladorCliente.listarTodos();
    }

    public List<Animal> listarTodosAnimaisPorTutorCpf(String tutorCpf) {
        return controladorAnimal.listarTodosAnimaisPorTutorCpf(tutorCpf);
    }

    public List<Veterinario> listarTodosVeterinarios() {
        return controladorVeterinario.listarTodos();
    }

    public DisponibilidadeAgenda getDisponibilidadeVeterinario(String crmv) {
        Veterinario veterinario = controladorVeterinario.buscarVeterinarioPorCrmv(crmv);
        return veterinario != null ? veterinario.getDisponibilidadeAgenda() : null;
    }

    public void removerDispoAgenda(String crmv, DiaSemana dia, LocalTime horario) {
        controladorVeterinario.removerDisponibilidade(crmv, dia, horario);
    }
}
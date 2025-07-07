package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dtos.*;
import com.clinicaveterinaria.negocio.entidades.*;

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
        Cliente cliente = new Cliente(
                clienteDTO.id(), clienteDTO.nome(),
                clienteDTO.sobrenome(), clienteDTO.cpf(),
                clienteDTO.telefone(), clienteDTO.email(), clienteDTO.endereco());
        controladorCliente.cadastrarCliente(cliente);
    };

    private Cliente buscaInternaCliente(String cpf) {
        return controladorCliente.buscarClientePorCpf(cpf);
    }

    public ClienteRespostaDTO buscarCliente(String cpf) {
        return buscaInternaCliente(cpf).paraDTO();
    };

//    public void atualizarCliente(String cpf);
//    public void removerCliente();

    public void cadastrarAnimal(AnimalRequisicaoDTO animalDTO) {
        Cliente cliente = buscaInternaCliente(animalDTO.tutorCPF());
        Animal animal =  new Animal(
                animalDTO.id(), cliente,
                animalDTO.nome(), animalDTO.especie(),
                animalDTO.raca(), animalDTO.dataNascimento(),
                animalDTO.peso(), animalDTO.identificacao()
        );
        controladorAnimal.cadastrarAnimal(animal);
    };

    private Animal buscaInternaAnimal(Long animalID) {
        return controladorAnimal.buscarAnimalPorId(animalID);
    };

    public AnimalRespostaDTO buscarAnimal(Long animalID) {
        return buscaInternaAnimal(animalID).paraDTO();
    }

//    public void atualizarAnimal();
//    public void removerAnimal();

    public void cadastrarVeterinario(VeterinarioRequisicaoDTO veterinarioDTO) {
        Veterinario veterinario = new Veterinario(
                veterinarioDTO.id(), veterinarioDTO.nome(),
                veterinarioDTO.sobrenome(), veterinarioDTO.crmv(),
                veterinarioDTO.email(), veterinarioDTO.telefone()
        );
        controladorVeterinario.cadastrarVeterinario(veterinario);
    }

    private Veterinario buscaInternaVeterinario(String crmv) {
        return controladorVeterinario.buscarVeterinarioPorCrmv(crmv);
    }

    public VeterinarioRespostaDTO buscarVeterinario(String crmv) {
        return buscaInternaVeterinario(crmv).paraDTO();
    }

    public void adiconarDispoAgenda(String crmv, DispoAgendaRequisicaoDTO agendaDTO) {
        Veterinario veterinario = buscaInternaVeterinario(crmv);
        DisponibilidadeAgenda agenda = agendaDTO.paraEntidade();
        veterinario.setDisponibilidadeAgenda(agenda);
    }
//    public void atualizarVeterinario();
//    public void removerVeterinario();

    public void cadastrarAgendamento(AgendamentoRequisicaoDTO agendamentoDTO) {
        Cliente cliente = controladorCliente.buscarClientePorCpf(agendamentoDTO.clienteCPF());
        Animal animal = controladorAnimal.buscarAnimalPorId(agendamentoDTO.animalId());
        Veterinario veterinario = controladorVeterinario.buscarVeterinarioPorCrmv(agendamentoDTO.veterinarioCRMV());
        System.out.println(veterinario.getDisponibilidadeAgenda());
        Agendamento agendamento = new Agendamento(
                agendamentoDTO.id(), cliente,
                animal, veterinario,
                agendamentoDTO.dataAgendamento(), agendamentoDTO.observacao(),
                agendamentoDTO.status()
        );
        controladorAgendamento.cadastrarAgendamento(agendamento);
    }

    private Agendamento buscaInternaAgendamento(Long agendamentoID) {
        return controladorAgendamento.buscarAgendamentoPorId(agendamentoID);
    }

    public AgendamentoRespostaDTO buscarAgendamento(Long agendamentoID) {
        return buscaInternaAgendamento(agendamentoID).paraDTO();
    }

    public void cadastrarAtendimento(AtendimentoRequisicaoDTO atendimentoRequisicaoDTO) {
        Agendamento agendamento = buscaInternaAgendamento(atendimentoRequisicaoDTO.agendamentoID());
        Atendimento atendimento = new Atendimento(
                atendimentoRequisicaoDTO.id(),
                agendamento,
                atendimentoRequisicaoDTO.dataRealizacao()
        );
        controladorAtendimento.cadastrarAtendimento(atendimento);
    }

    private Atendimento buscaInternaAtendimento(Long atendiemntoID) {
        return controladorAtendimento.buscarAtendimento(atendiemntoID);
    }

    public AtendimentoRespostaDTO buscarAtendimento(Long atendimentoID) {
        return buscaInternaAtendimento(atendimentoID).paraDTO();
    }
}
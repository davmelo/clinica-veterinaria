package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAgendamentos;
import com.clinicaveterinaria.dados.RepositorioAgendamentosArray;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;
import com.clinicaveterinaria.dtos.AgendamentoRequisicaoDTO;
import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Cliente;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ControladorAgendamento {

    private static ControladorAgendamento instance;
    final private IRepositorioAgendamentos repositorio;
    private final ControladorCliente controladorCliente;
    private final ControladorAnimal controladorAnimal;
    private final ControladorVeterinario controladorVeterinario;

    private ControladorAgendamento() {
        this.repositorio = RepositorioAgendamentosArray.getInstance();
        this.controladorCliente = ControladorCliente.getInstance();
        this.controladorAnimal = ControladorAnimal.getInstance();
        this.controladorVeterinario = ControladorVeterinario.getInstance();
    }

    public static ControladorAgendamento getInstance() {
        if (instance == null) {
            instance = new ControladorAgendamento();
        }
        return instance;
    }

    public boolean cadastrarAgendamento(AgendamentoRequisicaoDTO agendamentoDTO) {
        Cliente cliente = controladorCliente.buscarClientePorCpf(agendamentoDTO.clienteCPF());
        Animal animal = controladorAnimal.buscarAnimalPorId(agendamentoDTO.animalId());
        Veterinario veterinario = controladorVeterinario.buscarVeterinarioPorCrmv(agendamentoDTO.veterinarioCRMV());

        if (cliente == null) {
            System.err.println("Erro (ControladorAgendamento): Cliente com CPF " + agendamentoDTO.clienteCPF() + " não encontrado.");
            return false;
        }
        if (animal == null) {
            System.err.println("Erro (ControladorAgendamento): Animal com ID " + agendamentoDTO.animalId() + " não encontrado.");
            return false;
        }
        if (veterinario == null) {
            System.err.println("Erro (ControladorAgendamento): Veterinário com CRMV " + agendamentoDTO.veterinarioCRMV() + " não encontrado.");
            return false;
        }

        Agendamento agendamento = agendamentoDTO.paraEntidade(cliente, animal, veterinario);

        agendamento.setId(repositorio.gerarID());

        if (agendamento.getVeterinario() == null || agendamento.getDataAgendamento() == null) {
            System.err.println("Erro (ControladorAgendamento): Agendamento ou dados essenciais (Veterinário ou Data/Hora) são nulos.");
            return false;
        }

        if (verificarDisponibilidade(agendamento)) {
            // Verifica se já existe um agendamento com o mesmo ID (para o caso de atualização)
            if (agendamento.getId() != null && repositorio.buscar(agendamento.getId()) != null) {
                System.err.println("Erro (ControladorAgendamento): Agendamento com ID " + agendamento.getId() + " já existe. Considere atualizar.");
                return false;
            }
            repositorio.salvar(agendamento);
            System.out.println("Agendamento salvo no repositório: ID " + agendamento.getId());
            return true;
        } else {
            agendamento.setStatus(AgendamentoStatus.CANCELADO);
            return false;
        }
    }

    public Agendamento buscarAgendamentoPorId(Long id) {
        return repositorio.buscar(id);
    }

    public List<com.clinicaveterinaria.negocio.entidades.Agendamento> buscarAgendamentoPorDia(LocalDate data) {
        return repositorio.buscarPorDia(data);
    }

    public void atualizarStatus(Long id, AgendamentoStatus novoStatus) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            agendamento.setStatus(novoStatus);
            repositorio.atualizar(id, agendamento); // Persiste a mudança no repositório
            System.out.println("Agendamento " + id + " status atualizado para: " + novoStatus.name());
        } else {
            System.err.println("Erro (ControladorAgendamento): Agendamento com ID " + id + " não encontrado para atualizar status.");
        }
    }

    public void atualizarAgendamento(Long id, AgendamentoRequisicaoDTO novoAgendamentoDTO) {
        Agendamento agendamentoExistente = repositorio.buscar(id);
        if (agendamentoExistente == null) {
            System.err.println("Erro (ControladorAgendamento): Agendamento com ID " + id + " não encontrado para atualização.");
            return;
        }

        Cliente cliente = controladorCliente.buscarClientePorCpf(novoAgendamentoDTO.clienteCPF());
        Animal animal = controladorAnimal.buscarAnimalPorId(novoAgendamentoDTO.animalId());
        Veterinario veterinario = controladorVeterinario.buscarVeterinarioPorCrmv(novoAgendamentoDTO.veterinarioCRMV());

        if (cliente == null || animal == null || veterinario == null) {
            System.err.println("Erro (ControladorAgendamento): Dados de cliente, animal ou veterinário ausentes para a atualização do agendamento.");
            return;
        }
        Agendamento agendamentoParaVerificar = novoAgendamentoDTO.paraEntidade(cliente, animal, veterinario);
        agendamentoParaVerificar.setId(id);
        agendamentoParaVerificar.setTipoProcedimentoAgendado(novoAgendamentoDTO.tipoProcedimento());

        // Se a data/hora mudou, verifica a disponibilidade para a nova data/hora
        if (!agendamentoExistente.getDataAgendamento().equals(agendamentoParaVerificar.getDataAgendamento())) {
            if (!verificarDisponibilidade(agendamentoParaVerificar)) {
                System.err.println("Não foi possível atualizar: Novo horário indisponível para este veterinário.");
                return;
            }
        }

        agendamentoExistente.setCliente(cliente);
        agendamentoExistente.setAnimal(animal);
        agendamentoExistente.setVeterinario(veterinario);
        agendamentoExistente.setDataAgendamento(agendamentoParaVerificar.getDataAgendamento());
        agendamentoExistente.setObsevacao(agendamentoParaVerificar.getObsevacao());
        agendamentoExistente.setStatus(agendamentoParaVerificar.getStatus());
        agendamentoExistente.setTipoProcedimentoAgendado(agendamentoParaVerificar.getTipoProcedimentoAgendado());

        repositorio.atualizar(id, agendamentoExistente);
        System.out.println("Agendamento ID " + id + " atualizado.");
    }

    public void cancelarAgendamento(Long id, String motivo) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            agendamento.cancelar(motivo);
            repositorio.atualizar(id, agendamento);
            System.out.println("Agendamento " + id + " cancelado.");
        } else {
            System.out.println("Erro (ControladorAgendamento): Agendamento com ID " + id + " não encontrado para cancelamento.");
        }
    }

    public void remarcarAgendamento(Long id, LocalDateTime novaData, String motivo) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            // Cria um agendamento para verificar a disponibilidade do novo horário, mantem o mesmo ID para que o buscarPorVeterinarioEDataHora o ignore se for a mesma instância
            Agendamento agendamentoParaVerificar = new Agendamento();
            agendamentoParaVerificar.setId(id); // Importante para o equals/hashCode e para não dar conflito com ele mesmo
            agendamentoParaVerificar.setVeterinario(agendamento.getVeterinario());
            agendamentoParaVerificar.setDataAgendamento(novaData);
            agendamentoParaVerificar.setStatus(agendamento.getStatus()); // Manter o status para verificação

            if (verificarDisponibilidade(agendamentoParaVerificar)) {
                agendamento.remarcar(novaData, motivo);
                repositorio.atualizar(id, agendamento);
                System.out.println("Agendamento " + id + " remarcado para " + novaData);
            } else {
                System.out.println("Não foi possível remarcar: Novo horário indisponível para este veterinário.");
            }
        } else {
            System.out.println("Erro (ControladorAgendamento): Agendamento com ID " + id + " não encontrado para remarcação.");
        }
    }

    public void removerAgendamento(Long id) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            repositorio.remover(id);
            System.out.println("Agendamento " + id + " removido.");
        } else {
            System.out.println("Erro (ControladorAgendamento): Agendamento com ID " + id + " não encontrado para remover.");
        }
    }

    private boolean verificarDisponibilidade(Agendamento agendamento) {
        if (agendamento.getVeterinario() == null || agendamento.getVeterinario().getCrmv() == null || agendamento.getDataAgendamento() == null) {
            System.out.println("Dados essenciais (Veterinário ou Data/Hora) do agendamento são nulos.");
            return false;
        }

        DisponibilidadeAgenda disponibilidade = agendamento.getVeterinario().getDisponibilidadeAgenda();
        if (disponibilidade == null) {
            System.out.println("Veterinário sem agenda de disponibilidade cadastrada.");
            return false;
        }

        DiaSemana dia = DiaSemana.valueOf(agendamento.getDataAgendamento().getDayOfWeek().name());
        LocalTime hora = agendamento.getDataAgendamento().toLocalTime();

        if (!disponibilidade.estaDisponivel(dia, hora)) {
            System.out.println("Horário " + hora + " na " + dia + " não está na disponibilidade geral do veterinário.");
            return false;
        }

        Agendamento agendamentoExistenteNoHorario = repositorio.buscarPorVeterinarioEDataHora(
                agendamento.getVeterinario().getCrmv(), agendamento.getDataAgendamento()
        );

        if (agendamentoExistenteNoHorario != null && agendamentoExistenteNoHorario.getStatus() != AgendamentoStatus.CANCELADO) {
            if (agendamento.getId() != null && agendamentoExistenteNoHorario.getId() != null &&
                    agendamentoExistenteNoHorario.getId().equals(agendamento.getId())) {
                return true;
            } else {
                System.out.println("Erro (ControladorAgendamento): Já existe um agendamento ativo para este veterinário neste horário: " + agendamento.getDataAgendamento());
                return false;
            }
        }
        return true;
    }

    public List<Agendamento> listarTodosAgendamentos() {
        return repositorio.listarTodos();
    }

    public List<Agendamento> buscarPorVeterinario(String crmv) {
        return repositorio.buscarPorVeterinario(crmv);
    }
}
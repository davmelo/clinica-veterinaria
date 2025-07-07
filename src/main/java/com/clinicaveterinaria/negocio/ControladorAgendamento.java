package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAgendamentos;
import com.clinicaveterinaria.dados.RepositorioAgendamentosArray;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus; // Importe AgendamentoStatus
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ControladorAgendamento {

    private static ControladorAgendamento instance;
    final private IRepositorioAgendamentos repositorio;

    private ControladorAgendamento() {
        this.repositorio = RepositorioAgendamentosArray.getInstance();
    }

    public static ControladorAgendamento getInstance() {
        if (instance == null) {
            instance = new ControladorAgendamento();
        }
        return instance;
    }

    public boolean cadastrarAgendamento(Agendamento agendamento) {
        if (agendamento == null || agendamento.getVeterinario() == null || agendamento.getDataAgendamento() == null) {
            System.out.println("Dados de agendamento incompletos.");
            return false;
        }

        if (verificarDisponibilidade(agendamento)) {
            // Verificar se já existe um agendamento com o mesmo ID (Vai ser necessário atualizar)
            if (repositorio.buscar(agendamento.getId()) != null) {
                System.out.println("Agendamento com este ID já existe, você precisará atualizar.");
                return false;
            }
            repositorio.salvar(agendamento);
            agendamento.setStatus(AgendamentoStatus.AGENDADO);
            System.out.println("Agendamento realizado com sucesso!\n" + "Dados: Id Agendamento: " + agendamento.getId() + " Nome: " + agendamento.getCliente().getNome() + " Animal: " + agendamento.getAnimal().getNome() + " Horário: " + agendamento.getDataAgendamento() + "\n");
            return true;
        } else {
            System.out.println("Horário indisponível para este veterinário (Ocorreu conflito de agenda ou indisponibilidade no horário de trabalho).");
            agendamento.setStatus(AgendamentoStatus.CANCELADO);
            return false;
        }
    }

    public Agendamento buscarAgendamentoPorId(Long id) {
        return repositorio.buscar(id);
    }

    public void atualizarAgendamento(Long id, Agendamento novoAgendamento) {
        // Para a atualizar o agendamento temos que garantir que ele não será igual a outros agendamentos existentes
        Agendamento agendamentoExistente = repositorio.buscar(id);
        if (agendamentoExistente != null && agendamentoExistente.getDataAgendamento().equals(novoAgendamento.getDataAgendamento())) {
            // Se a data/hora não mudou, apenas atualiza
            repositorio.atualizar(id, novoAgendamento);
        } else {
            // Se a data/hora mudou, verifica a disponibilidade para a nova data/hora
            if (verificarDisponibilidade(novoAgendamento)) {
                repositorio.atualizar(id, novoAgendamento);
            } else {
                System.out.println("Não foi possível atualizar, novo horário indisponível para este veterinário.");
                agendamentoExistente.setStatus(AgendamentoStatus.CANCELADO); //analisar
            }
        }
    }

    public void cancelarAgendamento(Long id, String motivo) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            agendamento.cancelar(motivo);
            repositorio.atualizar(id, agendamento);
            System.out.println("Agendamento " + id + " cancelado.");
        } else {
            System.out.println("Agendamento com ID " + id + " não encontrado para cancelamento.");
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
            System.out.println("Agendamento com ID " + id + " não encontrado para remarcação.");
        }
    }

    public void removerAgendamento(Long id) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            repositorio.remover(id);
            System.out.println("Agendamento " + id + " removido.");
        } else {
            System.out.println("Agendamento com ID " + id + " não encontrado para remover.");
        }
    }

    private boolean verificarDisponibilidade(Agendamento agendamento) {
        //Verificar se o Veterinário e a Data/Hora do agendamento são válidos
        if (agendamento.getVeterinario() == null || agendamento.getVeterinario().getCrmv() == null || agendamento.getDataAgendamento() == null) {
            System.out.println("Dados essenciais (Veterinário ou Data/Hora) do agendamento são nulos.");
            return false;
        }

        //Verificar a disponibilidade do veterinário
        DisponibilidadeAgenda disponibilidade = agendamento.getVeterinario().getDisponibilidadeAgenda();
        if (disponibilidade == null) {
            System.out.println("Veterinário sem agenda de disponibilidade cadastrada.");
            return false;
        }

        DiaSemana dia = DiaSemana.valueOf(agendamento.getDataAgendamento().getDayOfWeek().name());
        LocalTime hora = agendamento.getDataAgendamento().toLocalTime();

        if (!disponibilidade.estaDisponivel(dia, hora)) {
            System.out.println("Horário " + hora + " na " + dia + " não está na disponibilidade geral do veterinário.");
            return false; // Horário fora da disponibilidade do veterinário
        }

        //Verificar se já existe um agendamento que gera conflito na agenda para este veterinário
        Agendamento agendamentoExistenteNoHorario = repositorio.buscarPorVeterinarioEDataHora(
                agendamento.getVeterinario().getCrmv(), agendamento.getDataAgendamento()
        );

        if (agendamentoExistenteNoHorario != null) {
            // Se encontrou um agendamento existente, verificar se é o mesmo agendamento que estamos tentando atualizar
            // Evitar que um agendamento conflita com ele mesmo ao ser atualizado)
            if (agendamento.getId() != null && agendamentoExistenteNoHorario.getId() != null &&
                    agendamentoExistenteNoHorario.getId().equals(agendamento.getId())) {
                // É o mesmo agendamento que está sendo atualizado, então não é um conflito
                return true;
            } else {
                System.out.println("Já existe um agendamento ativo para este veterinário neste horário: " + agendamento.getDataAgendamento());
                return false; // Conflito: horário já ocupado por outro agendamento
            }
        }


        return true; // Se chegou até aqui, o horário está disponível
    }

    public List<Agendamento> listarTodosAgendamentos() {
        return repositorio.listarTodos();
    }
}
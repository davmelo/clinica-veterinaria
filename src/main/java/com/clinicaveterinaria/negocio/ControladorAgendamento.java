package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAgendamentos;
import com.clinicaveterinaria.dados.RepositorioAgendamentosArray;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ControladorAgendamento {

    private final IRepositorioAgendamentos repositorio;

    public ControladorAgendamento() {
        this.repositorio = RepositorioAgendamentosArray.getInstance();
    }

    public boolean cadastrarAgendamento(Agendamento agendamento) {
        if (verificarDisponibilidade(agendamento)) {
            repositorio.salvar(agendamento);
            return true;
        } else {
            System.out.println("Horário indisponível para este veterinário.");
            return false;
        }
    }

    public Agendamento buscarAgendamentoPorId(Long id) {
        return repositorio.buscar(id);
    }

    public void atualizarAgendamento(Long id, Agendamento novoAgendamento) {
        repositorio.atualizar(id, novoAgendamento);
    }

    public void cancelarAgendamento(Long id, String motivo) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            agendamento.cancelar(motivo);
            repositorio.atualizar(id, agendamento);
        }
    }

    public void remarcarAgendamento(Long id, LocalDateTime novaData, String motivo) {
        Agendamento agendamento = repositorio.buscar(id);
        if (agendamento != null) {
            agendamento.remarcar(novaData, motivo);
            repositorio.atualizar(id, agendamento);
        }
    }

    public void removerAgendamento(Long id) {
        repositorio.remover(id);
    }

    private boolean verificarDisponibilidade(Agendamento agendamento) {
        if (agendamento.getVeterinario() == null || agendamento.getDataAgendamento() == null) return false;

        DisponibilidadeAgenda disponibilidade = agendamento.getVeterinario().getDisponibilidadeAgenda();
        if (disponibilidade == null) return false;

        DiaSemana dia = DiaSemana.valueOf(agendamento.getDataAgendamento().getDayOfWeek().name());
        LocalTime hora = agendamento.getDataAgendamento().toLocalTime();

        return disponibilidade.estaDisponivel(dia, hora);
    }

}

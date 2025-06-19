package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAgendamentos;
import com.clinicaveterinaria.dados.RepositorioAgendamentosArray;
import com.clinicaveterinaria.negocio.entidades.Agendamento;

import java.time.LocalDateTime;

public class ControladorAgendamento {

    private final IRepositorioAgendamentos repositorio;

    public ControladorAgendamento() {
        this.repositorio = RepositorioAgendamentosArray.getInstance();
    }

    public void cadastrarAgendamento(Agendamento agendamento) {
        repositorio.salvar(agendamento);
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
}

package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;


import java.util.ArrayList;
import java.util.List;

public class RepositorioAgendamentosArray implements IRepositorioAgendamentos {

    private static RepositorioAgendamentosArray instance;
    private List<Agendamento> agendamentos;

    private RepositorioAgendamentosArray() {
        this.agendamentos = new ArrayList<>();
    }

    public static IRepositorioAgendamentos getInstance() {
        if (instance == null) {
            instance = new RepositorioAgendamentosArray();
        }
        return instance;
    }

    @Override
    public void salvar(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    @Override
    public Agendamento buscar(Long id) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getId().equals(id)) {
                return agendamento;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Long id, Agendamento agendamentoAtualizado) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(id)) {
                agendamentos.set(i, agendamentoAtualizado);
                return;
            }
        }
    }

    @Override
    public void remover(Long id) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(id)) {
                agendamentos.remove(i);
                break;
            }
        }
    }
}

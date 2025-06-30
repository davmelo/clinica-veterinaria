package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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

    @Override
    public List<Agendamento> buscarPorVeterinario(String crmv) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getVeterinario() != null && agendamento.getVeterinario().getCrmv().equals(crmv)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorCliente(String cpf) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getCliente() != null && agendamento.getCliente().getCpf().equals(cpf)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorDia(LocalDate data) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDataAgendamento().toLocalDate().equals(data)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorStatus(AgendamentoStatus status) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getStatus() == status) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public Agendamento buscarPorVeterinarioEDataHora(String crmv, LocalDateTime dataHora) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getVeterinario() != null && agendamento.getVeterinario().getCrmv().equals(crmv) &&
                    agendamento.getDataAgendamento() != null && agendamento.getDataAgendamento().equals(dataHora)) {
                // Apenas agendamentos que não estão cancelados ou concluídos podem ocupar o horário
                if (agendamento.getStatus() != AgendamentoStatus.CANCELADO && agendamento.getStatus() != AgendamentoStatus.CONCLUIDO) {
                    return agendamento;
                }
            }
        }
        return null;
    }

    @Override
    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos); // Retorna uma cópia para evitar modificações externas diretas
    }

}

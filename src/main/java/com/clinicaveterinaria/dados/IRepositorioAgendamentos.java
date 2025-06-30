package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;

import java.time.LocalDate;
import java.util.List;

public interface IRepositorioAgendamentos {
    void salvar(Agendamento novoAgendamento);

    Agendamento buscar(Long id);

    void atualizar(Long id, Agendamento novoAgendamento);

    void remover(Long id);

    List<Agendamento> buscarPorVeterinario(String crmv);

    List<Agendamento> buscarPorCliente(String cpf);

    List<Agendamento> buscarPorDia(LocalDate data);

    List<Agendamento> buscarPorStatus(AgendamentoStatus status);
}
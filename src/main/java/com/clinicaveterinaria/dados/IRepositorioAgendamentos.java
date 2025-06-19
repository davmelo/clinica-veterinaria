package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;

public interface IRepositorioAgendamentos {
    void salvar(Agendamento novoAgendamento);

    Agendamento buscar(Long id);

    void atualizar(Long id, Agendamento novoAgendamento);

    void remover(Long id);
}
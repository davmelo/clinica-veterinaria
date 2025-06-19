package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;

public interface IRepositorioAgendamento {
    void salvar(Agendamento novoAgendamento);

    Agendamento buscarPorId(Long id);

    void atualizar(Long id, Agendamento novoAgendamento);

    void remover(Long id);
}
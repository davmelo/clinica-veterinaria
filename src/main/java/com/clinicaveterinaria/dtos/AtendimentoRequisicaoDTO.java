package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.Atendimento;

import java.time.LocalDateTime;

public record AtendimentoRequisicaoDTO(Long id, Long agendamentoID, LocalDateTime dataRealizacao) {
    public Atendimento paraEntidade(Agendamento agendamento) {
        return new Atendimento(id, agendamento, dataRealizacao);
    }
}

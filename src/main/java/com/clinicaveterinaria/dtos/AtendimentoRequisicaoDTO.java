package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.Atendimento;

import java.time.LocalDateTime;
import java.util.List;

public record AtendimentoRequisicaoDTO(Long id, Long agendamentoID, LocalDateTime dataRealizacao, List<ProcedimentoRealizadoRequisicaoDTO> procedimentosRealizados) {

    public Atendimento paraEntidade(Agendamento agendamento) {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(this.id);
        atendimento.setAgendamento(agendamento);
        atendimento.setDataRealizacao(this.dataRealizacao);
        return atendimento;
    }
}

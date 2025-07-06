package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.*;
import com.clinicaveterinaria.negocio.entidades.procedimento.SolicitacaoProcedimentos;

import java.time.LocalDateTime;

public record AgendamentoDTO(
        Long id,
        Long clienteId,
        Long animalId,
        Long veterinarioId,
        LocalDateTime dataAgendamento,
        String observacao,
        Long procedimentosSolicitadosId,
        AgendamentoStatus status
) {
    public Agendamento paraEntidade(Cliente cliente, Animal animal, Veterinario veterinario, SolicitacaoProcedimentos procedimentos) {
        return new Agendamento(
                id,
                cliente,
                animal,
                veterinario,
                dataAgendamento,
                observacao,
                procedimentos,
                status
        );
    }
}

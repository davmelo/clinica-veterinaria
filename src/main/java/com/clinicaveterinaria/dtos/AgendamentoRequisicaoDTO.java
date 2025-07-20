package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.*;
import com.clinicaveterinaria.negocio.entidades.procedimento.SolicitacaoProcedimentos;

import java.time.LocalDateTime;

public record AgendamentoRequisicaoDTO(
        Long id,
        String clienteCPF,
        Long animalId,
        String veterinarioCRMV,
        LocalDateTime dataAgendamento,
        String observacao,
        AgendamentoStatus status,
        String tipoProcedimento
) {
    public Agendamento paraEntidade(Cliente cliente, Animal animal, Veterinario veterinario) {
        return new Agendamento(
                id,
                cliente,
                animal,
                veterinario,
                dataAgendamento,
                observacao,
                status,
                tipoProcedimento
        );
    }
}

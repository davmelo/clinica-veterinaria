package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;

import java.time.LocalDateTime;

public record AgendamentoRespostaDTO(Long id, String nomeCliente, String nomeAnimal, String nomeVeterinario,
                                     LocalDateTime dataAgendamento, AgendamentoStatus status, String tipoProcedimento, String observacao) {
}

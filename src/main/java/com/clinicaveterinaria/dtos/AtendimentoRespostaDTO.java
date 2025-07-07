package com.clinicaveterinaria.dtos;

import java.time.LocalDateTime;

public record AtendimentoRespostaDTO(String nomeCliente, String nomeAnimal,
                                     String nomeVeterinario, LocalDateTime dataRealizacao) {
}

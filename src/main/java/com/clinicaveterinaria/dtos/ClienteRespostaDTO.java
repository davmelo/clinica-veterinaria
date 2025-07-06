package com.clinicaveterinaria.dtos;

public record ClienteRespostaDTO(String nome, String sobrenome,
                                 String cpf, String telefone, String email,
                                 String endereco) {
}

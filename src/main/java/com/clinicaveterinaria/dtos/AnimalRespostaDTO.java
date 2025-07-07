package com.clinicaveterinaria.dtos;

public record AnimalRespostaDTO(String tutorNome, String nome,
                                String especie, String raca,
                                int idade, double peso, String identificacao) {
}

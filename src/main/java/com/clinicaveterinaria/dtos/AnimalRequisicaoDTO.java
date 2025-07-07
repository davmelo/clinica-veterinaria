package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Cliente;

import java.time.LocalDate;

public record AnimalRequisicaoDTO(Long id, String tutorCPF, String nome,
                                  String especie, String raca, LocalDate dataNascimento,
                                  double peso, String identificacao) {

    public Animal paraEntidade(Cliente tutor) {
        return new Animal(id, tutor, nome, especie, raca, dataNascimento, peso, identificacao);
    }
}

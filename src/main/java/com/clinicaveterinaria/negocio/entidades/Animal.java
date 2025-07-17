package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.AnimalRespostaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Animal implements Serializable {

    private Long id;
    private Cliente tutor;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private double peso;
    private String identificacao;

    public int getIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    public AnimalRespostaDTO paraDTO() {
        return new AnimalRespostaDTO(tutor.getNome(), nome, especie, raca, getIdade(), peso, identificacao);
    }
}

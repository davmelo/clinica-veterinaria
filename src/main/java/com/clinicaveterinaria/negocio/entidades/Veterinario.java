package com.clinicaveterinaria.negocio.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Veterinario {
    private Long id;
    private String nome;
    private String sobrenome;
    private ArrayList<String> especialidades;
    private String telefone;
    private String crmv;
    private Agenda agenda;

    public void adicionarEspecialidade(String especialidade) {
        this.especialidades.add(especialidade);
    }
}

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
@EqualsAndHashCode(of = {"crmv", "email"})
public class Veterinario {
    private Long id;
    private String nome;
    private String sobrenome;
    private String crmv;
    private String telefone;
    private String email;
    private ArrayList<String> especialidades;
    //private Agenda agenda;

    public Veterinario(Long id, String nome, String sobrenome, String crmv, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.crmv = crmv;
        this.email = email;
        this.telefone = telefone;
    }

    private DisponibilidadeAgenda disponibilidadeAgenda;

    public void adicionarEspecialidade(String especialidade) {
        this.especialidades.add(especialidade);
    }
}

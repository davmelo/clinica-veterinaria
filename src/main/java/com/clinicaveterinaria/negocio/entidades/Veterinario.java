package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.VeterinarioRespostaDTO;
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
    private String senha;
    private ArrayList<String> especialidades;
    private DisponibilidadeAgenda disponibilidadeAgenda;

//    public Veterinario(Long id, String nome, String sobrenome, String crmv, String email, String senha, String telefone) {
//        this.id = id;
//        this.nome = nome;
//        this.sobrenome = sobrenome;
//        this.crmv = crmv;
//        this.email = email;
//        this.senha = senha;
//        this.telefone = telefone;
//        this.especialidades = (especialidades != null) ? especialidades : new ArrayList<>();
//        this.disponibilidadeAgenda = (disponibilidadeAgenda != null) ? disponibilidadeAgenda : new DisponibilidadeAgenda();
//    }

    public void adicionarEspecialidade(String especialidade) {
        if (this.especialidades == null) {
            this.especialidades = new ArrayList<>();
        }
        this.especialidades.add(especialidade);
    }

    public VeterinarioRespostaDTO paraDTO() {
        return new VeterinarioRespostaDTO(nome, sobrenome, crmv, telefone, email);
    }
}

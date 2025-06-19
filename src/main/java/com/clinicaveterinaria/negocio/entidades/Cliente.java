package com.clinicaveterinaria.negocio.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"cpf", "email"})
public class Cliente {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;


}

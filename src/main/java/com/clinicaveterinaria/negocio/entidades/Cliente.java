package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.ClienteRespostaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"cpf", "email"})
public class Cliente implements Serializable {
    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    public ClienteRespostaDTO paraDTO() {
        return new ClienteRespostaDTO(nome, sobrenome, cpf, telefone, email, endereco);
    }
}

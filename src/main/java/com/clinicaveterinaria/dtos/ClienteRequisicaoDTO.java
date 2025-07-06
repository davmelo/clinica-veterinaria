package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Cliente;

public record ClienteRequisicaoDTO(Long id, String nome, String sobrenome, String cpf, String telefone, String email, String endereco) {

    public Cliente paraEntidade() {
        return new Cliente(id, nome, sobrenome, cpf, telefone, email, endereco);
    }
}

package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Veterinario;

public record VeterinarioDTO(Long id, String nome, String sobrenome, String crmv, String email, String telefone) {

    public Veterinario paraEntidade() {
        return new Veterinario(id, nome, sobrenome, crmv, email, telefone);
    }
}

package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.Veterinario;
import java.util.ArrayList; //Testar depois modificar

public record VeterinarioRequisicaoDTO(Long id, String nome, String sobrenome, String crmv, String email, String senha, String telefone, ArrayList<String> especialidades) {

    public Veterinario paraEntidade() {
        return new Veterinario(id, nome, sobrenome, crmv, telefone, email, senha, especialidades, null);
    }
}

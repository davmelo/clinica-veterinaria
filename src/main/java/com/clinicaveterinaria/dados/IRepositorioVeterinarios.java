package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Veterinario;

import java.util.List;

public interface IRepositorioVeterinarios {
    void salvar(Veterinario novoVeterinario);

    Veterinario buscar(String crmv);

    Veterinario buscarPorEmail(String email);

    void atualizar(String crmv, Veterinario novoVeterinario);

    void remover(String crmv);

    List<Veterinario> listarTodos();

    Long gerarID();
}

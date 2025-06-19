package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioVeterinarios;
import com.clinicaveterinaria.dados.RepositorioVeterinariosArray;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

public class ControladorVeterinario {

    final private IRepositorioVeterinarios repositorio;

    public ControladorVeterinario() {
        this.repositorio = RepositorioVeterinariosArray.getInstance();
    }

    public void cadastrarVeterinario(Veterinario veterinario) {
        repositorio.salvar(veterinario);
    }

    public Veterinario buscarVeterinarioPorCrmv(String crmv) {
        return repositorio.buscar(crmv);
    }

    public void atualizarVeterinario(String crmv, Veterinario veterinarioAtualizado) {
        repositorio.atualizar(crmv, veterinarioAtualizado);
    }

    public void removerVeterinario(String crmv) {
        repositorio.remover(crmv);
    }
}
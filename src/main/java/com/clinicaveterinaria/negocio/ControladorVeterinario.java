package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioVeterinarios;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.dados.RepositorioVeterinariosArray;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

public class ControladorVeterinario {

    private static ControladorVeterinario instance;
    final private IRepositorioVeterinarios repositorio;

    private ControladorVeterinario() {
        this.repositorio = RepositorioVeterinariosArray.getInstance();
    }

    public static ControladorVeterinario getInstance() {
        if (instance == null) {
            instance = new ControladorVeterinario();
        }
        return instance;
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
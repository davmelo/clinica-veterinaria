package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioVeterinariosArray implements IRepositorioVeterinarios {
    private static RepositorioVeterinariosArray instance;
    private final List<Veterinario> veterinarios;

    private RepositorioVeterinariosArray() {
        this.veterinarios = new ArrayList<>();
    }

    public static IRepositorioVeterinarios getInstance() {
        if (instance == null) {
            instance = new RepositorioVeterinariosArray();
        }
        return instance;
    }

    @Override
    public void salvar(Veterinario novoVeterinario) {
        veterinarios.add(novoVeterinario);
    }

    @Override
    public Veterinario buscar(String crmv) {
        for (Veterinario vet : veterinarios) {
            if (vet.getCrmv().equals(crmv)) {
                return vet;
            }
        }
        return null;
    }

    @Override
    public Veterinario buscarPorEmail(String email) {
        for (Veterinario vet : veterinarios) {
            if (vet.getEmail() != null && vet.getEmail().equals(email)) {
                return vet;
            }
        }
        return null;
    }

    @Override
    public void atualizar(String crmv, Veterinario novoVeterinario) {
        for (int i = 0; i < veterinarios.size(); i++) {
            if (veterinarios.get(i).getCrmv().equals(crmv)) {
                veterinarios.set(i, novoVeterinario);
                return;
            }
        }
    }

    @Override
    public void remover(String crmv) {
        veterinarios.removeIf(vet -> vet.getCrmv().equals(crmv));
    }
}
package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.negocio.entidades.Veterinario;
import lombok.Getter;

public class UsuarioVeterinario {

    @Getter
    private static Veterinario veterinarioLogado;

    public static void setVeterinarioLogado(Veterinario veterinario) {
        UsuarioVeterinario.veterinarioLogado = veterinario;
    }

    public static Veterinario getVeterinarioLogado() {
        return veterinarioLogado;
    }

    public static void limparSessao() {
        veterinarioLogado = null;
    }

    public static boolean isVeterinarioLogado() {
        return veterinarioLogado != null;
    }
}
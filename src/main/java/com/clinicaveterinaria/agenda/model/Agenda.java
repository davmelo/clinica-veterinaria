package com.clinicaveterinaria.agenda.model;

import com.clinicaveterinaria.consulta.model.Agendamento;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.util.ArrayList;

public class Agenda {
    private Veterinario veterinario;
    private ArrayList<Agendamento> agendamentos;

    public Agenda(Veterinario veterinario) {
        setVeterinario(veterinario);
        this.agendamentos = new ArrayList<>();
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("");
        }

        this.veterinario = veterinario;
    }

    public boolean horarioDisponivel() {
        return false;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        this.agendamentos.add(agendamento);
    }
}

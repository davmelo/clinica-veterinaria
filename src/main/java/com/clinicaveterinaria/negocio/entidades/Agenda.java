package com.clinicaveterinaria.negocio.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Agenda {
    private Long id;
    private Veterinario veterinario;
    private List<Agendamento> agendamentos;

    public boolean horarioDisponivel() {
        return false;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        this.agendamentos.add(agendamento);
    }
}

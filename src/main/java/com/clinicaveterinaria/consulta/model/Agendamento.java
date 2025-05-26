package com.clinicaveterinaria.consulta.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDateTime;

public class Agendamento extends Consulta {

    public Agendamento(Cliente cliente, Animal animal, Veterinario veterinario, LocalDateTime dataAgedamento) {
        super(cliente, animal, veterinario, dataAgedamento);
    }

    @Override
    public void setData(LocalDateTime dataAgendamento) {
        if (dataAgendamento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("");
        }
        this.data = dataAgendamento;
    }
}

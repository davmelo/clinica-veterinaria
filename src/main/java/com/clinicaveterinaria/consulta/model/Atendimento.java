package com.clinicaveterinaria.consulta.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.procedimento.model.Procedimento;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Atendimento extends Consulta {
    private ArrayList<Procedimento> procedimentos;

    public Atendimento(Cliente cliente, Animal animal, Veterinario veterinario, LocalDateTime dataAgedamento) {
        super(cliente, animal, veterinario, dataAgedamento);
    }

    public Atendimento(Agendamento agendamento) {
        super(agendamento.getCliente(), agendamento.getAnimal(), agendamento.getVeterinario(), LocalDateTime.now());
    }

    @Override
    public void setData(LocalDateTime dataAtendimento) {
        if (dataAtendimento.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("");
        }
        this.data = dataAtendimento;
    }
}

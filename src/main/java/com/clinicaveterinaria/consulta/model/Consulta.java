package com.clinicaveterinaria.consulta.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDateTime;

public abstract class Consulta {
    protected Cliente cliente;
    protected Animal animal;
    protected Veterinario veterinario;
    protected LocalDateTime data;

    public Consulta(Cliente cliente, Animal animal, Veterinario veterinario, LocalDateTime data) {
        this.setCliente(cliente);
        this.setAnimal(animal);
        this.setVeterinario(veterinario);
        this.setData(data);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public LocalDateTime getData() {
        return data;
    }

    public abstract void setData(LocalDateTime data);

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}

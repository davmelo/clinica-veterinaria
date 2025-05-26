package com.clinicaveterinaria.procedimento.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDate;

public class Procedimento {
    protected String nome;
    protected LocalDate dataDeRealizacao;
    protected Veterinario veterinario;
    protected Animal animal;

    public Procedimento(String nome, LocalDate dataDeRealizacao, Veterinario veterinario, Animal animal) {
        this.setNome(nome);
        this.setDataDeRealizacao(dataDeRealizacao);
        this.setVeterinario(veterinario);
        this.setAnimal(animal);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeRealizacao() {
        return dataDeRealizacao;
    }

    public void setDataDeRealizacao(LocalDate dataDeRealizacao) {
        if (dataDeRealizacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("");
        }
        this.dataDeRealizacao = dataDeRealizacao;
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

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("");
        }
        this.animal = animal;
    }
}

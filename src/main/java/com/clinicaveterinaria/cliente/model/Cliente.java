package com.clinicaveterinaria.cliente.model;

import com.clinicaveterinaria.animal.model.Animal;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private ArrayList<Animal> animais;

    public Cliente(String nome, String telefone, String email, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.animais = new ArrayList<>();
    }

    public boolean adicionarAnimal(String nome, String especie, String raca, LocalDate dataNascimento, double peso, String identificacao) {
        Animal animal = new Animal(nome, especie, raca, dataNascimento, peso, identificacao, this);
        if(animal == null) {
            this.animais.add(animal);
            return true;
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Animal> getAnimais() {
        return animais;
    }
}

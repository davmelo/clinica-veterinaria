package com.clinicaveterinaria.cliente.model;

import com.clinicaveterinaria.animal.model.Animal;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private ArrayList<Animal> animais;

    public Cliente(String nome, String cpf, String telefone, String email, String endereco) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
        this.animais = new ArrayList<>();
    }

    public void adicionarAnimal(String nome, String especie, String raca, LocalDate dataNascimento, double peso, String identificacao) {
        Animal animal = new Animal(nome, especie, raca, dataNascimento, peso, identificacao, this);
        this.animais.add(animal);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    @Override
    public boolean equals(Object obj) {
        boolean retorno = false;
        if (obj instanceof Cliente clienteAux) {
            if (this.cpf.equals(clienteAux.getCpf())) retorno = true;
            }
        return retorno;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\tCPF: %s\nEmail: %s\tTelefone: %s\tNº pets: %d",
                this.nome, this.cpf, this.email, this.telefone, this.animais.size());

    }
}

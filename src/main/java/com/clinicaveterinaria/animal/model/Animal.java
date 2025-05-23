package com.clinicaveterinaria.animal.model;

import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.procedimento.model.Vacina;

import java.time.LocalDate;
import java.util.ArrayList;

public class Animal {
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private double peso;
    private String identificacao;
    private ArrayList<Vacina> vacinas;

    public Animal(String nome, String especie, String raca, LocalDate dataNascimento, double peso, String identificacao, Cliente tutor) {
        if(tutor == null) {
            throw new IllegalArgumentException("");
        }
        this.setNome(nome);
        this.setEspecie(especie);
        this.setRaca(raca);
        this.setDataNascimento(dataNascimento);
        this.setPeso(peso);
        this.vacinas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
}

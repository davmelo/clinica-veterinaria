package com.clinicaveterinaria.animal.model;

import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.procedimento.model.Vacina;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Animal {
    private Cliente tutor;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private double peso;
    private String identificacao;
    private ArrayList<Vacina> vacinas;

    public Animal(String nome, String especie, String raca, LocalDate dataNascimento, double peso, String identificacao, Cliente tutor) {
        this.setTutor(tutor);
        this.setNome(nome);
        this.setEspecie(especie);
        this.setRaca(raca);
        this.setDataNascimento(dataNascimento);
        this.setPeso(peso);
        this.setIdentificacao(identificacao);
        this.vacinas = new ArrayList<>();
    }

    public Cliente getTutor() {
        return tutor;
    }

    public void setTutor(Cliente tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException("");
        }
        this.tutor = tutor;
    }

    public ArrayList<Vacina> getVacinas() {
        return vacinas;
    }

    public void adiconarVacina(Vacina vacina) {
        if (vacina == null) {
            throw new IllegalArgumentException("");
        }
        this.vacinas.add(vacina);
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
        if (peso < 0.1) {
            throw new IllegalArgumentException("");
        }
        this.peso = peso;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object obj) {
        boolean retorno = false;
        if (obj instanceof Animal animalAux) {
            if (this.tutor.equals(animalAux.tutor) && this.nome.equals(animalAux.getNome())) {
                retorno = true;
            }
        }
        return retorno;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\tIdade: %d\nRaça: %s\tEspécie: %s",
                this.nome, this.getIdade(), this.raca, this.especie);
    }

}

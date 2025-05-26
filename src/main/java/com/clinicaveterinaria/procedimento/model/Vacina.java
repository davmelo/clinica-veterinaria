package com.clinicaveterinaria.procedimento.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDate;

public class Vacina extends Procedimento{
    private String nomeVacina;
    private int numeroDeDoses;
    private int doseNumero;
    private String tipoAplicacao;
    private LocalDate validade;

    public Vacina(String nome, LocalDate dataDeRealizacao, Veterinario veterinario, Animal animal,
                  int numeroDeDoses, int doseNumero, String tipoAplicacao, LocalDate validade, String nomeVacina) {
        super(nome, dataDeRealizacao, veterinario, animal);
        this.setNome(nome);
        this.setNomeVacina(nomeVacina);
        this.setNumeroDeDoses(numeroDeDoses);
        this.setDoseNumero(doseNumero);
        this.setTipoAplicacao(tipoAplicacao);
        this.setValidade(validade);
    }

    public String getNomeVacina(){
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina){
        this.nomeVacina = nomeVacina;
    }
    
    public int getNumeroDeDoses() {
        return numeroDeDoses;
    }
    
    public void setNumeroDeDoses(int numeroDeDoses) {
        if (numeroDeDoses < 1) {
            throw new IllegalArgumentException("");
        }
        this.numeroDeDoses = numeroDeDoses;
    }

    public String getTipoAplicacao() {
        return tipoAplicacao;
    }

    public void setTipoAplicacao(String tipoAplicacao) {
        this.tipoAplicacao = tipoAplicacao;
    }

    public int getDoseNumero() {
        return doseNumero;
    }

    public void setDoseNumero(int doseNumero) {
        if (doseNumero < 1) {
            throw new IllegalArgumentException("");
        }
        this.doseNumero = doseNumero;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }
}

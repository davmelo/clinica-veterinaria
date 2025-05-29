package com.clinicaveterinaria.solicitacaoprocedimento.model;

import com.clinicaveterinaria.animal.model.Animal;

import java.time.LocalDate;

public class SolicitacaoProcedimento {
    String nomeVetSolicitante;
    String crmvVetSolicitante;
    Animal animal;
    String tipoProcedimento;
    String nomeProcedimento;
    LocalDate dataSolicitacao;
    String observacao;

    public SolicitacaoProcedimento(String observacao, String nomeVetSolicitante, String crmvVetSolicitante, Animal animal, String tipoProcedimento, String nomeProcedimento, LocalDate dataSolicitacao) {
        this.setNomeVetSolicitante(nomeVetSolicitante);
        this.setCrmvVetSolicitante(crmvVetSolicitante);
        this.setAnimal(animal);
        this.setTipoProcedimento(tipoProcedimento);
        this.setNomeProcedimento(nomeProcedimento);
        this.setDataSolicitacao(dataSolicitacao);
        this.setObservacao(observacao);
    }

    public String getNomeVetSolicitante() {
        return nomeVetSolicitante;
    }

    public void setNomeVetSolicitante(String nomeVetSolicitante) {
        this.nomeVetSolicitante = nomeVetSolicitante;
    }

    public String getCrmvVetSolicitante() {
        return crmvVetSolicitante;
    }

    public void setCrmvVetSolicitante(String crmvVetSolicitante) {
        this.crmvVetSolicitante = crmvVetSolicitante;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(String tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public String getNomeProcedimento() {
        return nomeProcedimento;
    }

    public void setNomeProcedimento(String nomeProcedimento) {
        this.nomeProcedimento = nomeProcedimento;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

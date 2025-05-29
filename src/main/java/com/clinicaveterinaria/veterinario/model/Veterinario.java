package com.clinicaveterinaria.veterinario.model;

import com.clinicaveterinaria.agenda.model.Agenda;

import java.util.ArrayList;

public class Veterinario {
    private String nome;
    private ArrayList<String> especialidades;
    private String telefone;
    private String crmv;
    private Agenda agenda;

    public Veterinario(String nome, String especialidade, String telefone, String crmv, Agenda agenda) {
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setCrmv(crmv);
        this.especialidades = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getEspecialidades() {
        return especialidades;
    }

    public void adicionarEspecialidade(String especialidade) {
        this.especialidades.add(especialidade);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retorno = false;
        if (obj instanceof Veterinario veterinarioAux) {
            if (this.crmv.equals((veterinarioAux.crmv))) retorno = true;
        }
        return retorno;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\tRegistro: %s", this.nome, this.crmv);
    }
}

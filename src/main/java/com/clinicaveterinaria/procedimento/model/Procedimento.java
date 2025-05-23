package com.clinicaveterinaria.procedimento.model;

import java.time.LocalDate;

public class Procedimento {
    LocalDate dataDeRealizacao;

    public LocalDate getDataDeRealizacao() {
        return dataDeRealizacao;
    }

    public void setDataDeRealizacao(LocalDate dataDeRealizacao) {
        this.dataDeRealizacao = dataDeRealizacao;
    }
}

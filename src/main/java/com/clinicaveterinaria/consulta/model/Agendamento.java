package com.clinicaveterinaria.consulta.model;

import com.clinicaveterinaria.animal.model.Animal;
import com.clinicaveterinaria.cliente.model.Cliente;
import com.clinicaveterinaria.solicitacaoprocedimento.model.SolicitacaoProcedimento;
import com.clinicaveterinaria.veterinario.model.Veterinario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Agendamento extends Consulta {
    private ArrayList<SolicitacaoProcedimento> procedimenotosSolicidatos;

    public Agendamento(Cliente cliente, Animal animal, Veterinario veterinario, LocalDateTime dataAgedamento) {
        super(cliente, animal, veterinario, dataAgedamento);
        this.procedimenotosSolicidatos = new ArrayList<>();
    }

    @Override
    public void setData(LocalDateTime dataAgendamento) {
        if (dataAgendamento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("");
        }
        this.data = dataAgendamento;
    }

    public ArrayList<SolicitacaoProcedimento> getProcedimenotosSolicidatos() {
        return this.procedimenotosSolicidatos;
    }

    public void adicionarSolicitacaoProcedimento(SolicitacaoProcedimento procedimentoSol) {
        if (procedimentoSol == null) {
            throw new IllegalArgumentException("");
        }
        this.procedimenotosSolicidatos.add(procedimentoSol);
    }
}

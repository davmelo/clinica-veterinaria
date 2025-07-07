package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.Atendimento;

import java.util.ArrayList;
import java.util.List;

public class RepositorioAtendimentosArray implements IRepositorioAtendimentos {

    private static RepositorioAtendimentosArray instance;
    private List<Atendimento> atendimentos;

    private RepositorioAtendimentosArray() {
        this.atendimentos = new ArrayList<>();
    }

    public static IRepositorioAtendimentos getInstance() {
        if (instance == null) {
            instance = new RepositorioAtendimentosArray();
        }
        return instance;
    }

    @Override
    public void salvar(Atendimento atendimento) {
        atendimentos.add(atendimento);
    }

    @Override
    public Atendimento buscar(Long id) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.getId().equals(id)) {
                return atendimento;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Long id, Atendimento atendimentoAtualizado) {
        for (int i = 0; i < atendimentos.size(); i++) {
            if (atendimentos.get(i).getId().equals(id)) {
                atendimentos.set(i, atendimentoAtualizado);
                return;
            }
        }
    }

    @Override
    public void remover(Long id) {
        for (int i = 0; i < atendimentos.size(); i++) {
            if (atendimentos.get(i).getId().equals(id)) {
                atendimentos.remove(i);
                break;
            }
        }
    }

}

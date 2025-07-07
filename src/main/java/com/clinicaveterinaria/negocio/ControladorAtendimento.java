package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAtendimentos;
import com.clinicaveterinaria.dados.RepositorioAtendimentosArray;
import com.clinicaveterinaria.negocio.entidades.Atendimento;

public class ControladorAtendimento {

    private static ControladorAtendimento instance;
    final private IRepositorioAtendimentos repositorio;

    private ControladorAtendimento() {
        this.repositorio = RepositorioAtendimentosArray.getInstance();
    }

    public static ControladorAtendimento getInstance() {
        if (instance == null) {
            instance = new ControladorAtendimento();
        }
        return instance;
    }

    public void cadastrarAtendimento(Atendimento atendimento) {
        repositorio.salvar(atendimento);
    }

    public Atendimento buscarAtendimento(Long id) {
        return repositorio.buscar(id);
    }

    public void atualizarAtendimento(Long id, Atendimento novoAtendimento) {
        repositorio.atualizar(id, novoAtendimento);
    }

    public void removerAtendimento(Long id) {
        repositorio.remover(id);
    }
}

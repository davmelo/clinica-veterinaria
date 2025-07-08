package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Atendimento;
import java.util.List;

public interface IRepositorioAtendimentos {
    void salvar(Atendimento atendimento);

    Atendimento buscar(Long id);

    void atualizar(Long id, Atendimento novoAtendimento);

    void remover(Long id);

    List<Atendimento> listarTodos();
}

package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Cliente;

import java.util.List;

public interface IRepositorioClientes {
    void salvar(Cliente novoCliente);

    Cliente buscar(String cpf);

    void atualizar(String cpf, Cliente novoCliente);

    void remover(String cpf);

    List<Cliente> listarTodos();

    Long gerarID();
}

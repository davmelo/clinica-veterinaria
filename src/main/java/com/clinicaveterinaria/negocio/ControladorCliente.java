package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioClientes;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Cliente;

import java.util.List;

public class ControladorCliente {

    final private IRepositorioClientes repositorio;

    public ControladorCliente() {
        this.repositorio = RepositorioClientesArray.getInstance();
    }

    public void cadastrarCliente(Cliente cliente) {
        repositorio.salvar(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return repositorio.buscar(cpf);
    }

    public void atualizarCliente(String cpf, Cliente clienteAtualizado) {
        repositorio.atualizar(cpf, clienteAtualizado);
    }

    public void removerCliente(String cpf) {
        repositorio.remover(cpf);
    }
}


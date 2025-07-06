package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioClientes;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Cliente;


public class ControladorCliente {

    private static ControladorCliente instance;
    final private IRepositorioClientes repositorio;

    private ControladorCliente() {
        this.repositorio = RepositorioClientesArray.getInstance();
    }

    public static ControladorCliente getInstance() {
        if (instance == null) {
            instance = new ControladorCliente();
        }
        return instance;
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


package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RepositorioClientesArray implements IRepositorioClientes {
    private static RepositorioClientesArray instance;
    private List<Cliente> clientes;

    private RepositorioClientesArray() {
        this.clientes = new ArrayList<>();
    }

    public static IRepositorioClientes getInstance() {
        if (instance == null) {
            instance = new RepositorioClientesArray();
        }
        return instance;
    }

    @Override
    public void salvar(Cliente novoCliente) {
        if (novoCliente != null && novoCliente.getCpf() != null){
            clientes.add(novoCliente);
        }
    }

    @Override
    public Cliente buscar(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public void atualizar(String cpf, Cliente novoCliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cpf)) {
                clientes.set(i, novoCliente);
                return;
            }
        }
    }

    @Override
    public void remover(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }
}

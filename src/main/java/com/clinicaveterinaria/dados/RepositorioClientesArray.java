package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioClientesArray implements IRepositorioClientes, Serializable {

    private static RepositorioClientesArray instance;

    private final List<Cliente> clientes;

    private RepositorioClientesArray() {
        this.clientes = new ArrayList<>();
    }

    public static IRepositorioClientes getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    private static RepositorioClientesArray lerDoArquivo() {
        RepositorioClientesArray instanciaLocal = null;

        File in = new File("clientes.dat");
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioClientesArray) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioClientesArray();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {/* Silent exception */
                }
            }
        }

        return instanciaLocal;
    }

    public void salvarArquivo() {
        if (instance == null) {
            return;
        }
        File out = new File("clientes.dat");
        FileOutputStream fos;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    /* Silent */}
            }
        }
    }

    @Override
    public void salvar(Cliente novoCliente) {
        if (novoCliente != null && novoCliente.getCpf() != null){
            clientes.add(novoCliente);
        }
        salvarArquivo();
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
                i = clientes.size();
            }
        }
        salvarArquivo();
    }

    @Override
    public void remover(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        salvarArquivo();
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes); // Retorna uma cópia da lista interna
    }

    @Override
    public Long gerarID() {
        return (long) clientes.size();
    }

}

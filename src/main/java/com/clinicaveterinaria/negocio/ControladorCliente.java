package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioClientes;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.dtos.ClienteRequisicaoDTO;
import com.clinicaveterinaria.negocio.entidades.Cliente;
import java.util.List;


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

    public void cadastrarCliente(ClienteRequisicaoDTO clienteDTO) { // Recebe o DTO
        if (repositorio.buscar(clienteDTO.cpf()) != null) {
            System.err.println("Erro (ControladorCliente): Cliente com CPF " + clienteDTO.cpf() + " já cadastrado.");
            return;
        }

        Cliente cliente = clienteDTO.paraEntidade();
        cliente.setId(repositorio.gerarID());
        repositorio.salvar(cliente);
        System.out.println("Cliente cadastrado no repositório: " + cliente.getNome() + " - " + cliente.getCpf());
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return repositorio.buscar(cpf);
    }

    public void atualizarCliente(ClienteRequisicaoDTO clienteDTO) {
        Cliente clienteExistente = repositorio.buscar(clienteDTO.cpf());
        if (clienteExistente == null) {
            System.err.println("Erro (ControladorCliente): Cliente com CPF " + clienteDTO.cpf() + " não encontrado para atualização.");
            return;
        }
        Cliente clienteAtualizado = clienteDTO.paraEntidade();
        repositorio.atualizar(clienteDTO.cpf(), clienteAtualizado);
        System.out.println("Cliente atualizado no repositório: " + clienteAtualizado.getNome() + " - " + clienteAtualizado.getCpf());
    }

    public void removerCliente(String cpf) {
        Cliente clienteExistente = repositorio.buscar(cpf);
        if (clienteExistente == null) {
            System.err.println("Erro (ControladorCliente): Cliente com CPF " + cpf + " não encontrado para remoção.");
            return;
        }
        repositorio.remover(cpf);
        System.out.println("Cliente removido do repositório: " + cpf);
    }

    public List<Cliente> listarTodos() {
        return ((RepositorioClientesArray) repositorio).listarTodos();
    }
}


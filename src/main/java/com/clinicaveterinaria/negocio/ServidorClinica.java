package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.negocio.entidades.Cliente;

public class ServidorClinica {
    private static ServidorClinica instance;
    private ControladorCliente controladorCliente;
    private ControladorAnimal controladorAnimal;
    private ControladorVeterinario controladorVeterinario;
    private ControladorAgendamento controladorAgendamento;

    private ServidorClinica() {
        controladorCliente = ControladorCliente.getInstance();
        controladorAnimal = ControladorAnimal.getInstance();
        controladorVeterinario = ControladorVeterinario.getInstance();
        controladorAgendamento = ControladorAgendamento.getInstance();
    }

    public static ServidorClinica getInstance() {
        if (instance == null) {
            instance = new ServidorClinica();
        }
        return instance;
    }

    public void cadastrarCliente(
            String nome, String sobrenome, String cpf,
            String telefone, String email, String endereco) {
        Cliente cliente = new Cliente(1L, nome, sobrenome, cpf, telefone, email, endereco);
        controladorCliente.cadastrarCliente(cliente);
    };
    public Cliente buscarCliente(String cpf) {
        controladorCliente.buscarClientePorCpf(cpf);
    };
    public void atualizarCliente();
    public void removerCliente();

    public void cadastrarAnimal();
    public void buscarAnimal();
    public void atualizarAnimal();
    public void removerAnimal();

    public void cadastrarVeterinario();
    public void buscarVeterinario();
    public void atualizarVeterinario();
    public void removerVeterinario();
}

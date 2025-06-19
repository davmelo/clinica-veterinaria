package com.clinicaveterinaria.application;

import com.clinicaveterinaria.negocio.ControladorCliente;
import com.clinicaveterinaria.negocio.ControladorAnimal;
import com.clinicaveterinaria.negocio.entidades.Cliente;
import com.clinicaveterinaria.negocio.entidades.Animal;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ControladorCliente clienteController = new ControladorCliente();
        ControladorAnimal animalController = new ControladorAnimal();

        // Criar e cadastrar cliente
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("João");
        cliente1.setSobrenome("Silva");
        cliente1.setCpf("12345678900");
        cliente1.setTelefone("99999-9999");
        cliente1.setEmail("joao@email.com");
        cliente1.setEndereco("Rua A, 123");
        clienteController.cadastrarCliente(cliente1);

        // Buscar cliente
        Cliente cBuscado = clienteController.buscarClientePorCpf("12345678900");
        System.out.println("Cliente buscado: " + cBuscado);

        // Criar e cadastrar animal
        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setNome("Rex");
        animal1.setEspecie("Cachorro");
        animal1.setRaca("Labrador");
        animal1.setDataNascimento(LocalDate.of(2018, 5, 20));
        animal1.setPeso(30.5);
        animal1.setIdentificacao("A001");
        animal1.setTutor(cBuscado);  // Relacionar ao cliente
        animalController.cadastrarAnimal(animal1);

        // Buscar animal
        Animal aBuscado = animalController.buscarAnimalPorId(1L);
        System.out.println("Animal buscado: " + aBuscado);

        // Atualizar cliente (exemplo)
        cliente1.setTelefone("88888-8888");
        clienteController.atualizarCliente(cliente1.getCpf(), cliente1);

        // Atualizar animal (exemplo)
        animal1.setPeso(32.0);
        animalController.atualizarAnimal(animal1.getId(), animal1);

        // Remover cliente e animal
        animalController.removerAnimal(animal1.getId());
        clienteController.removerCliente(cliente1.getCpf());

        System.out.println("Teste concluído.");
    }
}

//package com.clinicaveterinaria.application;
//
//import com.clinicaveterinaria.negocio.entidades.*;
//import com.clinicaveterinaria.negocio.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        ControladorCliente clienteController = new ControladorCliente();
//        ControladorAnimal animalController = new ControladorAnimal();
//        ControladorVeterinario veterinarioController = new ControladorVeterinario();
//
//        // Criar e cadastrar cliente
//        Cliente cliente1 = new Cliente();
//        cliente1.setId(1L);
//        cliente1.setNome("João");
//        cliente1.setSobrenome("Silva");
//        cliente1.setCpf("12345678900");
//        cliente1.setTelefone("99999-9999");
//        cliente1.setEmail("joao@email.com");
//        cliente1.setEndereco("Rua A, 123");
//        clienteController.cadastrarCliente(cliente1);
//
//        Cliente cliente2 = new Cliente();
//        cliente2.setId(2L);
//        cliente2.setNome("Pedro");
//        cliente2.setSobrenome("Atlas");
//        cliente2.setCpf("12345678999");
//        cliente2.setTelefone("85999-9999");
//        cliente2.setEmail("pedro@email.com");
//        cliente2.setEndereco("Rua B, 123");
//        clienteController.cadastrarCliente(cliente2);
//
//        // Buscar cliente
//        Cliente cBuscado = clienteController.buscarClientePorCpf("12345678900");
//        System.out.println("Cliente buscado: " + cBuscado);
//
//        // Criar e cadastrar animal
//        Animal animal1 = new Animal();
//        animal1.setId(1L);
//        animal1.setNome("Rex");
//        animal1.setEspecie("Cachorro");
//        animal1.setRaca("Labrador");
//        animal1.setDataNascimento(LocalDate.of(2018, 5, 20));
//        animal1.setPeso(30.5);
//        animal1.setIdentificacao("A001");
//        animal1.setTutor(cBuscado);  // Relacionar ao cliente
//        animalController.cadastrarAnimal(animal1);
//
//        Animal animal2 = new Animal();
//        animal2.setId(2L);
//        animal2.setNome("Pluto");
//        animal2.setEspecie("Gato");
//        animal2.setRaca("Siames");
//        animal2.setDataNascimento(LocalDate.of(2019, 7, 11));
//        animal2.setPeso(18.20);
//        animal2.setIdentificacao("A002");
//        animal2.setTutor(cBuscado);  // Relacionar ao cliente
//        animalController.cadastrarAnimal(animal2);
//
//        // Buscar animal
//        Animal aBuscado = animalController.buscarAnimalPorId(1L);
//        System.out.println("Animal buscado: " + aBuscado);
//
//        //Criar e cadastrar veterinario
//        Veterinario vet1 = new Veterinario();
//        vet1.setId(1L);
//        vet1.setNome("Paula");
//        vet1.setSobrenome("Oliveira");
//        vet1.setCrmv("CRMV1234");
//        vet1.setTelefone("55555-5555");
//        vet1.setEmail("paula@vetclinic.com");
//        vet1.setEspecialidades(new ArrayList<>());
//        vet1.adicionarEspecialidade("Clínico Geral");
//
//        veterinarioController.cadastrarVeterinario(vet1);
//
//        //Buscar veterinario (exemplo)
//        Veterinario vBuscado = veterinarioController.buscarVeterinarioPorCrmv("CRMV1234");
//        System.out.println("Veterinário buscado: " + vBuscado);
//
//        // Atualizar cliente (exemplo)
//        cliente1.setTelefone("88888-8888");
//        clienteController.atualizarCliente(cliente1.getCpf(), cliente1);
//
//        // Atualizar animal (exemplo)
//        animal1.setPeso(32.0);
//        animalController.atualizarAnimal(animal1.getId(), animal1);
//
//        // Atualizar veterinario (exemplo)
//        vet1.setTelefone("66666-6666");
//        vet1.adicionarEspecialidade("Dermatologia");
//        veterinarioController.atualizarVeterinario("CRMV1234", vet1);
//
//        // Remover cliente, animal e veterinario
//        animalController.removerAnimal(animal1.getId());
//        clienteController.removerCliente(cliente1.getCpf());
//        veterinarioController.removerVeterinario("CRMV1234");
//
//        System.out.println("Teste concluído.\n\n");
//
//        //Início do teste de agendamento (verifica disponibilidade) - São adicionados mais 2 veterinários.
//
//        Veterinario vet2 = new Veterinario();
//        vet2.setId(2L);
//        vet2.setNome("Ana");
//        vet2.setSobrenome("Costa");
//        vet2.setCrmv("CRMV5678");
//        vet2.setTelefone("91234-5678");
//        vet2.setEmail("ana@vet.com");
//        vet2.setEspecialidades(new ArrayList<>());
//        vet2.adicionarEspecialidade("Ortopedia");
//
//        Veterinario vet3 = new Veterinario();
//        vet3.setId(21L);
//        vet3.setNome("Antonio");
//        vet3.setSobrenome("Neto");
//        vet3.setCrmv("CRMV5337");
//        vet3.setTelefone("94587-9878");
//        vet3.setEmail("antonio@vet.com");
//        vet3.setEspecialidades(new ArrayList<>());
//        vet3.adicionarEspecialidade("Dermatologia");
//
//        //Disponibilidade Paula Oliveira
//        DisponibilidadeAgenda disponibilidade1 = new DisponibilidadeAgenda();
//        disponibilidade1.adicionarHorario(DiaSemana.MONDAY, LocalTime.of(10, 0)); // Segunda-feira 10h
//        disponibilidade1.adicionarHorario(DiaSemana.MONDAY, LocalTime.of(11, 0)); // Segunda-feira 11h
//        vet2.setDisponibilidadeAgenda(disponibilidade1);
//
//        //Disponibilidade Ana Costa
//        DisponibilidadeAgenda disponibilidade2 = new DisponibilidadeAgenda();
//        disponibilidade2.adicionarHorario(DiaSemana.THURSDAY, LocalTime.of(9, 30)); // Quinta-feira 9:30h
//        disponibilidade2.adicionarHorario(DiaSemana.THURSDAY, LocalTime.of(10, 30)); // Quinta-feira 10:30h
//        vet3.setDisponibilidadeAgenda(disponibilidade2);
//
//        //Disponibilidade Antonio Neto
//        DisponibilidadeAgenda disponibilidade3 = new DisponibilidadeAgenda();
//        disponibilidade3.adicionarHorario(DiaSemana.MONDAY, LocalTime.of(15, 0)); // Segunda-feira 15:00h
//        disponibilidade3.adicionarHorario(DiaSemana.THURSDAY, LocalTime.of(16, 0)); // Quinta-feira 16:00h
//        vet3.setDisponibilidadeAgenda(disponibilidade3);
//
//        ControladorAgendamento controladorAgendamento = new ControladorAgendamento();
//        Agendamento agendamento1 = new Agendamento();
//        agendamento1.setId(1L);
//        agendamento1.setCliente(cliente1);
//        agendamento1.setAnimal(animal1);
//        agendamento1.setVeterinario(vet2);
//        agendamento1.setDataAgendamento(LocalDateTime.of(2025, 6, 23, 10, 0)); // Segunda-feira
//        agendamento1.setObsevacao("Consulta ortopédica");
//        agendamento1.setStatus(AgendamentoStatus.PENDENTE);
//
//        Agendamento agendamento2= new Agendamento();
//        agendamento2.setId(2L);
//        agendamento2.setCliente(cliente2);
//        agendamento2.setAnimal(animal2);
//        agendamento2.setVeterinario(vet2);
//        agendamento2.setDataAgendamento(LocalDateTime.of(2025, 6, 23, 10, 0)); // Segunda-feira
//        agendamento2.setObsevacao("Consulta ortopédica");
//        agendamento2.setStatus(AgendamentoStatus.PENDENTE);
//
//        Agendamento agendamento3= new Agendamento();
//        agendamento3.setId(4L);
//        agendamento3.setCliente(cliente2);
//        agendamento3.setAnimal(animal2);
//        agendamento3.setVeterinario(vet3);
//        agendamento3.setDataAgendamento(LocalDateTime.of(2025, 6, 26, 16, 0)); // Quinta-feira
//        agendamento3.setObsevacao("Consulta Dermatologica");
//        agendamento3.setStatus(AgendamentoStatus.PENDENTE);
//
//        controladorAgendamento.cadastrarAgendamento(agendamento1);
//        System.out.println(agendamento1);
//
//        System.out.println("\n------------------\n");
//
//        controladorAgendamento.cadastrarAgendamento(agendamento2);
//        System.out.println(agendamento2);
//
//        System.out.println("\n------------------\n");
//
//        controladorAgendamento.cadastrarAgendamento(agendamento3);
//        System.out.println(agendamento3);
//
//        System.out.println("\nTodos os Agendamentos:\n");
//        List<Agendamento> todosAgendamentos = controladorAgendamento.listarTodosAgendamentos();
//        if (todosAgendamentos.isEmpty()) {
//            System.out.println("Nenhum agendamento cadastrado.");
//        } else {
//            for (Agendamento agendamento : todosAgendamentos) {
//                System.out.println(agendamento);
//            }
//        }
//        System.out.println("\n------------------\n");
//
//    }
//}
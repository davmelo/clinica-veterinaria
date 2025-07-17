package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAnimais;
import com.clinicaveterinaria.dados.RepositorioAnimaisArray;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Cliente;
import com.clinicaveterinaria.dtos.AnimalRequisicaoDTO;

import java.util.ArrayList;
import java.util.List;

public class ControladorAnimal {

    private static ControladorAnimal instance;
    private final ControladorCliente controladorCliente;
    final private IRepositorioAnimais repositorio;

    private ControladorAnimal() {
        this.repositorio = RepositorioAnimaisArray.getInstance();
        this.controladorCliente = ControladorCliente.getInstance();
    }

    public static ControladorAnimal getInstance() {
        if (instance == null) {
            instance = new ControladorAnimal();
        }
        return instance;
    }

    public void cadastrarAnimal(AnimalRequisicaoDTO animalDTO) {
        Cliente tutor = controladorCliente.buscarClientePorCpf(animalDTO.tutorCPF());
        if (tutor == null) {
            System.err.println("Erro (ControladorAnimal): Tutor com CPF " + animalDTO.tutorCPF() + " não encontrado para o animal.");
            return;
        }

        Animal animal = animalDTO.paraEntidade(tutor);

        if (animal.getId() == null) {
            animal.setId(repositorio.gerarID());
        }

        repositorio.salvar(animal);
        System.out.println("Animal cadastrado no repositório: " + animal.getNome() + " - ID: " + animal.getId() + " - Tutor: " + animal.getTutor().getNome());
    }

    public Animal buscarAnimalPorId(Long id) {
        if (id == null) {
            System.err.println("Erro (ControladorAnimal): Digite um Id valido.");
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    public void atualizarAnimal(Long id, AnimalRequisicaoDTO animalDTOAtualizado) {
        Animal animalExistente = buscarAnimalPorId(id);
        if (animalExistente == null) {
            System.err.println("Erro (ControladorAnimal): Animal com ID " + id + " não encontrado para atualização.");
            return;
        }
        Cliente novoTutor = controladorCliente.buscarClientePorCpf(animalDTOAtualizado.tutorCPF());
        if (novoTutor == null) {
            System.err.println("Erro (ControladorAnimal): Novo tutor com CPF " + animalDTOAtualizado.tutorCPF() + " não encontrado para atualização do animal.");
            return;
        }

        animalExistente.setNome(animalDTOAtualizado.nome());
        animalExistente.setEspecie(animalDTOAtualizado.especie());
        animalExistente.setRaca(animalDTOAtualizado.raca());
        animalExistente.setDataNascimento(animalDTOAtualizado.dataNascimento());
        animalExistente.setPeso(animalDTOAtualizado.peso());
        animalExistente.setIdentificacao(animalDTOAtualizado.identificacao());
        animalExistente.setTutor(novoTutor);

        repositorio.atualizar(id, animalExistente);
        System.out.println("Animal atualizado no repositório: " + animalExistente.getNome() + " - ID: " + id);
    }

    public void removerAnimal(Long id) {
        if (id == null) {
            System.err.println("Erro (ControladorAnimal): ID de animal não pode ser nulo para remoção.");
            return;
        }
        Animal animalExistente = repositorio.buscarPorId(id);
        if (animalExistente == null) {
            System.err.println("Erro (ControladorAnimal): Animal com ID " + id + " não encontrado para remoção.");
            return;
        }
        repositorio.remover(id);
        System.out.println("Animal removido do repositório: ID " + id + " Nome: " + animalExistente.getNome() + " Tutor: " + animalExistente.getTutor().getNome());
    }

    public List<Animal> listarTodos() {
        return ((RepositorioAnimaisArray) repositorio).listarTodos();
    }

    public List<Animal> listarTodosAnimaisPorTutorCpf(String tutorCpf) {
        if (tutorCpf == null || tutorCpf.isEmpty()) {
            System.err.println("Erro (ControladorAnimal): CPF do tutor não pode ser nulo ou vazio para buscar animais.");
            return new ArrayList<>();
        }
        return ((RepositorioAnimaisArray) repositorio).buscarPorTutorCpf(tutorCpf);
    }
}


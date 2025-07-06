package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAnimais;
import com.clinicaveterinaria.dados.RepositorioAnimaisArray;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.negocio.entidades.Animal;

import java.util.List;

public class ControladorAnimal {

    private static ControladorAnimal instance;
    final private IRepositorioAnimais repositorio;

    private ControladorAnimal() {
        this.repositorio = RepositorioAnimaisArray.getInstance();
    }

    public static ControladorAnimal getInstance() {
        if (instance == null) {
            instance = new ControladorAnimal();
        }
        return instance;
    }

    public void cadastrarAnimal(Animal animal) {
        repositorio.salvar(animal);
    }

    public Animal buscarAnimalPorId(Long id) {
        return repositorio.buscarPorId(id);
    }

    public void atualizarAnimal(Long id, Animal animalAtualizado) {
        repositorio.atualizar(id, animalAtualizado);
    }

    public void removerAnimal(Long id) {
        repositorio.remover(id);
    }

    // Opcional - listar todos os animais (se implementar no repositório)
    public List<Animal> listarTodos() {
        throw new UnsupportedOperationException("Método listarTodos não implementado");
    }
}


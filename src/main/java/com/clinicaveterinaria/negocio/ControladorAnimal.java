package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAnimais;
import com.clinicaveterinaria.dados.RepositorioAnimaisArray;
import com.clinicaveterinaria.negocio.entidades.Animal;

import java.util.List;

public class ControladorAnimal {

    final private IRepositorioAnimais repositorio;

    public ControladorAnimal() {
        this.repositorio = RepositorioAnimaisArray.getInstance();
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


package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Animal;

import java.util.List;

public interface IRepositorioAnimais {
    void salvar(Animal novoAnimal);

    Animal buscarPorId(Long id);

    void atualizar(Long id, Animal novoAnimal);

    void remover(Long id);

    List<Animal> listarTodos();

    List<Animal> buscarPorTutorCpf(String cpf);

    Long gerarID();
}

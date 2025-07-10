package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Animal;

import java.util.ArrayList;
import java.util.List;

public class RepositorioAnimaisArray implements IRepositorioAnimais {

    private static RepositorioAnimaisArray instance;
    private final List<Animal> animais;

    private RepositorioAnimaisArray() {
        this.animais = new ArrayList<>();
    }

    public static IRepositorioAnimais getInstance() {
        if (instance == null) {
            instance = new RepositorioAnimaisArray();
        }
        return instance;
    }

    @Override
    public void salvar(Animal novoAnimal) {
        animais.add(novoAnimal);
    }

    @Override
    public Animal buscarPorId(Long id) {
        for (Animal animal : animais) {
            if (animal.getId().equals(id)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Long id, Animal novoAnimal) {
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getId().equals(id)) {
                animais.set(i, novoAnimal);
                return;
            }
        }
    }

    @Override
    public void remover(Long id) {
        animais.removeIf(animal -> animal.getId().equals(id));
    }

    @Override
    public List<Animal> listarTodos() {
        return new ArrayList<>(animais);
    }

    @Override
    public List<Animal> buscarPorTutorCpf(String cpf) {
        List<Animal> animaisDoTutor = new ArrayList<>();
        for (Animal animal : animais) {
            if (animal.getTutor() != null && animal.getTutor().getCpf().equals(cpf)) {
                animaisDoTutor.add(animal);
            }
        }
        return animaisDoTutor;
    }
}

package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Animal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAnimaisArray implements IRepositorioAnimais, Serializable {

    private static RepositorioAnimaisArray instance;

    private final List<Animal> animais;

    private RepositorioAnimaisArray() {
        this.animais = new ArrayList<>();
    }

    public static IRepositorioAnimais getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    private static RepositorioAnimaisArray lerDoArquivo() {
        RepositorioAnimaisArray instanciaLocal = null;

        File in = new File("animais.dat");
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioAnimaisArray) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioAnimaisArray();
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
        File out = new File("animais.dat");
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
    public void salvar(Animal novoAnimal) {
        animais.add(novoAnimal);
        salvarArquivo();
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
                i = animais.size();
            }
        }
        salvarArquivo();
    }

    @Override
    public void remover(Long id) {
        animais.removeIf(animal -> animal.getId().equals(id));
        salvarArquivo();
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

    @Override
    public Long gerarID() {
        return (long) animais.size();
    }
}

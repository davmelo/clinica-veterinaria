package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Veterinario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVeterinariosArray implements IRepositorioVeterinarios, Serializable {

    private static RepositorioVeterinariosArray instance;

    private final List<Veterinario> veterinarios;

    private RepositorioVeterinariosArray() {
        this.veterinarios = new ArrayList<>();
    }

    public static IRepositorioVeterinarios getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    private static RepositorioVeterinariosArray lerDoArquivo() {
        RepositorioVeterinariosArray instanciaLocal = null;

        File in = new File("veterinarios.dat");
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioVeterinariosArray) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioVeterinariosArray();
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
        File out = new File("veterinarios.dat");
        FileOutputStream fos;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
            System.out.println("Alterações salvas no arquivo");
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
    public void salvar(Veterinario novoVeterinario) {
        veterinarios.add(novoVeterinario);
        salvarArquivo();
    }

    @Override
    public Veterinario buscar(String crmv) {
        for (Veterinario vet : veterinarios) {
            if (vet.getCrmv().equals(crmv)) {
                return vet;
            }
        }
        return null;
    }

    @Override
    public Veterinario buscarPorEmail(String email) {
        for (Veterinario vet : veterinarios) {
            if (vet.getEmail() != null && vet.getEmail().equals(email)) {
                return vet;
            }
        }
        return null;
    }

    @Override
    public void atualizar(String crmv, Veterinario novoVeterinario) {
        for (int i = 0; i < veterinarios.size(); i++) {
            if (veterinarios.get(i).getCrmv().equals(crmv)) {
                veterinarios.set(i, novoVeterinario);
                i = veterinarios.size();
            }
        }
        salvarArquivo();
    }

    @Override
    public void remover(String crmv) {
        veterinarios.removeIf(vet -> vet.getCrmv().equals(crmv));
        salvarArquivo();
    }

    @Override
    public List<Veterinario> listarTodos() {
        return new ArrayList<>(veterinarios);
    }

    @Override
    public Long gerarID() {
        return (long) veterinarios.size();
    }
}
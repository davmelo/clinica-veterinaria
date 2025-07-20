package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Atendimento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAtendimentosArray implements IRepositorioAtendimentos, Serializable {

    private static RepositorioAtendimentosArray instance;

    private final List<Atendimento> atendimentos;

    private RepositorioAtendimentosArray() {
        this.atendimentos = new ArrayList<>();
    }

    public static IRepositorioAtendimentos getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    private static RepositorioAtendimentosArray lerDoArquivo() {
        RepositorioAtendimentosArray instanciaLocal = null;

        File in = new File("atendimentos.dat");
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioAtendimentosArray) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioAtendimentosArray();
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
        File out = new File("atendimentos.dat");
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
    public void salvar(Atendimento atendimento) {
        atendimentos.add(atendimento);
    }

    @Override
    public Atendimento buscar(Long id) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.getId().equals(id)) {
                return atendimento;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Long id, Atendimento atendimentoAtualizado) {
        for (int i = 0; i < atendimentos.size(); i++) {
            if (atendimentos.get(i).getId().equals(id)) {
                atendimentos.set(i, atendimentoAtualizado);
                i = atendimentos.size();
            }
        }
        salvarArquivo();
    }

    @Override
    public void remover(Long id) {
        for (int i = 0; i < atendimentos.size(); i++) {
            if (atendimentos.get(i).getId().equals(id)) {
                atendimentos.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Atendimento> listarTodos() {
        return new ArrayList<>(atendimentos);
    }

    public int gerarID() {
        return atendimentos.size();
    }
}

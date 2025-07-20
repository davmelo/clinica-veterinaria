package com.clinicaveterinaria.dados;

import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.AgendamentoStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class RepositorioAgendamentosArray implements IRepositorioAgendamentos, Serializable {

    private static RepositorioAgendamentosArray instance;

    private final List<Agendamento> agendamentos;

    private RepositorioAgendamentosArray() {
        this.agendamentos = new ArrayList<>();
    }

    public static IRepositorioAgendamentos getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    private static RepositorioAgendamentosArray lerDoArquivo() {
        RepositorioAgendamentosArray instanciaLocal = null;

        File in = new File("agendamentos.dat");
        FileInputStream fis;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioAgendamentosArray) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioAgendamentosArray();
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
        File out = new File("agendamentos.dat");
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
    public void salvar(Agendamento agendamento) {
        agendamentos.add(agendamento);
        salvarArquivo();
    }

    @Override
    public Agendamento buscar(Long id) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getId().equals(id)) {
                return agendamento;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Long id, Agendamento agendamentoAtualizado) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(id)) {
                agendamentos.set(i, agendamentoAtualizado);
                i = agendamentos.size();
            }
        }
        salvarArquivo();
    }

    @Override
    public void remover(Long id) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(id)) {
                agendamentos.remove(i);
                break;
            }
        }
        salvarArquivo();
    }

    @Override
    public List<Agendamento> buscarPorVeterinario(String crmv) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getVeterinario() != null && agendamento.getVeterinario().getCrmv().equals(crmv)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorCliente(String cpf) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getCliente() != null && agendamento.getCliente().getCpf().equals(cpf)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorDia(LocalDate data) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getDataAgendamento().toLocalDate().equals(data)) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public List<Agendamento> buscarPorStatus(AgendamentoStatus status) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getStatus() == status) {
                resultado.add(agendamento);
            }
        }
        return resultado;
    }

    @Override
    public Agendamento buscarPorVeterinarioEDataHora(String crmv, LocalDateTime dataHora) {
        for (Agendamento agendamento : agendamentos) {
            if (agendamento.getVeterinario() != null && agendamento.getVeterinario().getCrmv().equals(crmv) &&
                    agendamento.getDataAgendamento() != null && agendamento.getDataAgendamento().equals(dataHora)) {
                // Apenas agendamentos que não estão cancelados ou concluídos podem ocupar o horário
                if (agendamento.getStatus() != AgendamentoStatus.CANCELADO && agendamento.getStatus() != AgendamentoStatus.CONCLUIDO) {
                    return agendamento;
                }
            }
        }
        return null;
    }

    @Override
    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos); // Retorna uma cópia para evitar modificações externas diretas
    }

    @Override
    public Long gerarID() {
        return (long) agendamentos.size();
    }

}

package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioVeterinarios;
import com.clinicaveterinaria.dados.RepositorioVeterinariosArray;
import com.clinicaveterinaria.dtos.DispoAgendaRequisicaoDTO;
import com.clinicaveterinaria.dtos.VeterinarioRequisicaoDTO;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import java.util.ArrayList;
import java.util.List;

public class ControladorVeterinario {

    private static ControladorVeterinario instance;
    final private IRepositorioVeterinarios repositorio;

    private ControladorVeterinario() {
        this.repositorio = RepositorioVeterinariosArray.getInstance();
    }

    public static ControladorVeterinario getInstance() {
        if (instance == null) {
            instance = new ControladorVeterinario();
        }
        return instance;
    }

    public void cadastrarVeterinario(VeterinarioRequisicaoDTO veterinarioDTO) {
        if (repositorio.buscar(veterinarioDTO.crmv()) != null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + veterinarioDTO.crmv() + " já cadastrado.");
            return;
        }
        Veterinario veterinario = veterinarioDTO.paraEntidade();
        if (veterinario.getEspecialidades() == null) {
            veterinario.setEspecialidades(new ArrayList<>());
        }
        if (veterinario.getDisponibilidadeAgenda() == null) {
            veterinario.setDisponibilidadeAgenda(new DisponibilidadeAgenda());
        }
        repositorio.salvar(veterinario);
        System.out.println("Veterinário cadastrado no repositório: " + veterinario.getNome() + " - " + veterinario.getCrmv());
    }

    public Veterinario buscarVeterinarioPorCrmv(String crmv) {
        return repositorio.buscar(crmv);
    }

    // Metodo para uso interno
    public void atualizarVeterinario(String crmv, Veterinario novoVeterinario) {
        Veterinario veterinarioExistente = repositorio.buscar(crmv);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para atualização.");
            return;
        }

        veterinarioExistente.setNome(novoVeterinario.getNome());
        veterinarioExistente.setSobrenome(novoVeterinario.getSobrenome());
        veterinarioExistente.setEmail(novoVeterinario.getEmail());
        veterinarioExistente.setTelefone(novoVeterinario.getTelefone());
        veterinarioExistente.setEspecialidades(new ArrayList<>(novoVeterinario.getEspecialidades()));
        veterinarioExistente.setDisponibilidadeAgenda(novoVeterinario.getDisponibilidadeAgenda());

        repositorio.atualizar(crmv, veterinarioExistente);
        System.out.println("Veterinário atualizado no repositório: " + veterinarioExistente.getNome() + " - CRMV: " + crmv);
    }

    // Metodo para uso externo, como ServidorClinica pelo DTO
    public void atualizarVeterinario(String crmv, VeterinarioRequisicaoDTO veterinarioDTO) {
        Veterinario veterinarioExistente = repositorio.buscar(crmv);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para atualização via DTO.");
            return;
        }
        veterinarioExistente.setNome(veterinarioDTO.nome());
        veterinarioExistente.setSobrenome(veterinarioDTO.sobrenome());
        veterinarioExistente.setEmail(veterinarioDTO.email());
        veterinarioExistente.setTelefone(veterinarioDTO.telefone());
        veterinarioExistente.setEspecialidades(veterinarioDTO.especialidades() != null ? new ArrayList<>(veterinarioDTO.especialidades()) : new ArrayList<>());

        repositorio.atualizar(crmv, veterinarioExistente);
        System.out.println("Veterinário atualizado no repositório via DTO: " + veterinarioExistente.getNome() + " - CRMV: " + crmv);
    }


    public void removerVeterinario(String crmv) {
        Veterinario veterinarioExistente = repositorio.buscar(crmv);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para remoção.");
            return;
        }
        repositorio.remover(crmv);
        System.out.println("Veterinário removido do repositório: " + crmv);
    }

    public void adiconarDispoAgenda(String crmv, DispoAgendaRequisicaoDTO agendaDTO) {
        Veterinario veterinario = this.buscarVeterinarioPorCrmv(crmv);
        if (veterinario != null) {
            if (veterinario.getDisponibilidadeAgenda() == null) {
                veterinario.setDisponibilidadeAgenda(new DisponibilidadeAgenda());
            }
            veterinario.getDisponibilidadeAgenda().adicionarHorario(agendaDTO.dia(), agendaDTO.horario());

            this.atualizarVeterinario(veterinario.getCrmv(), veterinario);
            System.out.println("Disponibilidade adicionada para " + veterinario.getNome() + " em " + agendaDTO.dia() + " às " + agendaDTO.horario());
        } else {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para adicionar disponibilidade.");
        }
    }
}
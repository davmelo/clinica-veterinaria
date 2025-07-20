package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioVeterinarios;
import com.clinicaveterinaria.dados.RepositorioClientesArray;
import com.clinicaveterinaria.dados.RepositorioVeterinariosArray;
import com.clinicaveterinaria.dtos.DispoAgendaRequisicaoDTO;
import com.clinicaveterinaria.dtos.VeterinarioRequisicaoDTO;
import com.clinicaveterinaria.negocio.entidades.Cliente;
import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

import java.time.LocalTime;
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
        String crmvNormalizado = veterinarioDTO.crmv().toUpperCase().replaceAll("\\s+", "");
        String emailNormalizado = veterinarioDTO.email().toLowerCase();

        if (repositorio.buscar(crmvNormalizado) != null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + veterinarioDTO.crmv() + " já cadastrado.");
            return;
        }

        if (repositorio.buscarPorEmail(emailNormalizado) != null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com email " + emailNormalizado + " já cadastrado.");
            return;
        }

        Veterinario veterinario = veterinarioDTO.paraEntidade();
        veterinario.setCrmv(crmvNormalizado);
        veterinario.setEmail(emailNormalizado);
        veterinario.setId(repositorio.gerarID());

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
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");
        return repositorio.buscar(crmvNormalizado);
    }

    // Metodo para uso interno
    public void atualizarVeterinario(String crmv, Veterinario novoVeterinario) {
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");
        Veterinario veterinarioExistente = repositorio.buscar(crmvNormalizado);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para atualização.");
            return;
        }

        String novoCrmvNormalizado = novoVeterinario.getCrmv().toUpperCase().replaceAll("\\s+", "");
        String novoEmailNormalizado = novoVeterinario.getEmail().toLowerCase();

        veterinarioExistente.setNome(novoVeterinario.getNome());
        veterinarioExistente.setSobrenome(novoVeterinario.getSobrenome());
        veterinarioExistente.setEmail(novoEmailNormalizado);
        veterinarioExistente.setCrmv(novoCrmvNormalizado);
        veterinarioExistente.setTelefone(novoVeterinario.getTelefone());
        veterinarioExistente.setEspecialidades(new ArrayList<>(novoVeterinario.getEspecialidades()));
        veterinarioExistente.setDisponibilidadeAgenda(novoVeterinario.getDisponibilidadeAgenda());

        repositorio.atualizar(crmv, veterinarioExistente);
        System.out.println("Veterinário atualizado no repositório: " + veterinarioExistente.getNome() + " - CRMV: " + crmv);
    }

    // Metodo para uso externo, como ServidorClinica pelo DTO
    public void atualizarVeterinario(String crmv, VeterinarioRequisicaoDTO veterinarioDTO) {
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");
        Veterinario veterinarioExistente = repositorio.buscar(crmvNormalizado);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para atualização via DTO.");
            return;
        }

//        String novoEmailNormalizado = veterinarioDTO.email().toLowerCase();
//        String novoCrmvNormalizado = veterinarioDTO.crmv().toUpperCase().replaceAll("\\s+", "");

        veterinarioExistente.setTelefone(veterinarioDTO.telefone());
        veterinarioExistente.setSenha(veterinarioDTO.senha());
        //        veterinarioExistente.setEspecialidades(veterinarioDTO.especialidades() != null ? new ArrayList<>(veterinarioDTO.especialidades()) : new ArrayList<>());

        repositorio.atualizar(crmvNormalizado, veterinarioExistente);
        System.out.println("Veterinário atualizado no repositório via DTO: " + veterinarioExistente.getNome() + " - CRMV: " + crmvNormalizado);
    }


    public void removerVeterinario(String crmv) {
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");

        Veterinario veterinarioExistente = repositorio.buscar(crmvNormalizado);
        if (veterinarioExistente == null) {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmv + " não encontrado para remoção.");
            return;
        }
        repositorio.remover(crmv);
        System.out.println("Veterinário removido do repositório: " + crmvNormalizado);
    }

    public void adiconarDispoAgenda(String crmv, DispoAgendaRequisicaoDTO agendaDTO) {
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");

        Veterinario veterinario = this.buscarVeterinarioPorCrmv(crmvNormalizado);
        if (veterinario != null) {
            if (veterinario.getDisponibilidadeAgenda() == null) {
                veterinario.setDisponibilidadeAgenda(new DisponibilidadeAgenda());
            }
            veterinario.getDisponibilidadeAgenda().adicionarHorario(agendaDTO.dia(), agendaDTO.horario());

            this.atualizarVeterinario(veterinario.getCrmv(), veterinario);
            System.out.println("Disponibilidade adicionada para " + veterinario.getNome() + " em " + agendaDTO.dia() + " às " + agendaDTO.horario());
        } else {
            System.err.println("Erro (ControladorVeterinario): Veterinário com CRMV " + crmvNormalizado + " não encontrado para adicionar disponibilidade.");
        }
    }

    public Veterinario autenticar(String identificador, String senha) {
        Veterinario veterinario = null;

        String identificadorNormalizadoCrmv = identificador.toUpperCase().replaceAll("\\s+", ""); // Para CRMV
        String identificadorNormalizadoEmail = identificador.toLowerCase(); // Para Email


        // Tenta buscar por CRMV primeiro
        veterinario = repositorio.buscar(identificadorNormalizadoCrmv);

        // Se não encontrou por CRMV, tenta buscar por email
        if (veterinario == null) {
            veterinario = repositorio.buscarPorEmail(identificadorNormalizadoEmail);
        }

        if (veterinario != null) {
            if (veterinario.getSenha() != null && veterinario.getSenha().equals(senha)) {
                return veterinario; // Retorna o veterinário se a senha estiver correta
            }
        }
        System.err.println("Erro (ControladorVeterinario): Falha na autenticação para identificador: " + identificador);
        return null;
    }

    public void removerDisponibilidade(String crmv, DiaSemana dia, LocalTime horario) {
        String crmvNormalizado = crmv.toUpperCase().replaceAll("\\s+", "");
        Veterinario veterinario = this.buscarVeterinarioPorCrmv(crmvNormalizado);
        if (veterinario != null) {
            if (veterinario.getDisponibilidadeAgenda() != null) {
                veterinario.getDisponibilidadeAgenda().removerHorario(dia, horario);
                this.atualizarVeterinario(veterinario.getCrmv(), veterinario);
                System.out.println("Disponibilidade removida para " + veterinario.getNome() + " em " + dia + " às " + horario);
            } else {
                System.err.println("Erro (ControladorVeterinario): Veterinário " + crmvNormalizado + " não possui agenda.");
            }
        } else {
            System.err.println("Erro (ControladorVeterinario): Veterinário " + crmvNormalizado + " não encontrado para remover disponibilidade.");
        }
    }

    public List<Veterinario> listarTodos() {
        return ((RepositorioVeterinariosArray) repositorio).listarTodos();
    }
}
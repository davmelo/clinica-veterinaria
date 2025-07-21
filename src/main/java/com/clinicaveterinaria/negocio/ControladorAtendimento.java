package com.clinicaveterinaria.negocio;

import com.clinicaveterinaria.dados.IRepositorioAtendimentos;
import com.clinicaveterinaria.dados.RepositorioAtendimentosArray;
import com.clinicaveterinaria.dtos.AtendimentoRequisicaoDTO;
import com.clinicaveterinaria.dtos.ProcedimentoRealizadoRequisicaoDTO;
import com.clinicaveterinaria.negocio.entidades.Agendamento;
import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Atendimento;
import com.clinicaveterinaria.negocio.entidades.Veterinario;
import com.clinicaveterinaria.negocio.entidades.procedimento.*;


import java.util.ArrayList;
import java.util.List;

public class ControladorAtendimento {

    private static ControladorAtendimento instance;
    final private IRepositorioAtendimentos repositorio;
    private final ControladorAgendamento controladorAgendamento;
    private final ControladorVeterinario controladorVeterinario;
    private final ControladorAnimal controladorAnimal;
    private long nextAtendimentoId = 0L;

    private ControladorAtendimento() {
        this.repositorio = RepositorioAtendimentosArray.getInstance();
        this.controladorAgendamento = ControladorAgendamento.getInstance();
        this.controladorVeterinario = ControladorVeterinario.getInstance();
        this.controladorAnimal = ControladorAnimal.getInstance();
    }

    public static ControladorAtendimento getInstance() {
        if (instance == null) {
            instance = new ControladorAtendimento();
        }
        return instance;
    }

    public void cadastrarAtendimento(AtendimentoRequisicaoDTO atendimentoRequisicaoDTO) {
        //Busca o agendamento associado (responsabilidade do Controlador)
        Agendamento agendamento = controladorAgendamento.buscarAgendamentoPorId(atendimentoRequisicaoDTO.agendamentoID());
        if (agendamento == null) {
            System.err.println("Erro (ControladorAtendimento): Agendamento com ID " + atendimentoRequisicaoDTO.agendamentoID() + " não encontrado para o atendimento.");
            return;
        }

        //Mapeia a lista de ProcedimentoRealizadoRequisicaoDTO para entidades ProcedimentoRealizado
        List<ProcedimentoRealizado> listaDeProcedimentosRealizados = new ArrayList<>();
        if (atendimentoRequisicaoDTO.procedimentosRealizados() != null) {
            for (ProcedimentoRealizadoRequisicaoDTO procDTO : atendimentoRequisicaoDTO.procedimentosRealizados()) {
                Veterinario vetQueRealizou = controladorVeterinario.buscarVeterinarioPorCrmv(procDTO.veterinarioCrmv());
                Animal animalProcedimento = controladorAnimal.buscarAnimalPorId(procDTO.animalId());

                if (vetQueRealizou == null) {
                    System.err.println("Aviso (ControladorAtendimento): Veterinário com CRMV " + procDTO.veterinarioCrmv() + " para o procedimento '" + procDTO.nomeProcedimento() + "' não encontrado. Procedimento ignorado.");
                    continue;
                }
                if (animalProcedimento == null) {
                    System.err.println("Aviso (ControladorAtendimento): Animal com ID " + procDTO.animalId() + " para o procedimento '" + procDTO.nomeProcedimento() + "' não encontrado. Procedimento ignorado.");
                    continue;
                }

                ProcedimentoRealizado procedimentoRealizado = null;
                switch (procDTO.tipoProcedimento()) {
                    case CIRURGIA:
                        Cirurgia cirurgia = new Cirurgia();
                        cirurgia.setId(procDTO.id());
                        cirurgia.setTipoProcedimento(procDTO.tipoProcedimento());
                        cirurgia.setNomeProcedimento(procDTO.nomeProcedimento());
                        cirurgia.setVeterinario(vetQueRealizou);
                        cirurgia.setAnimal(animalProcedimento);
                        cirurgia.setDataDeRealizacao(procDTO.dataRealizacao());
                        cirurgia.setParteCorpo(procDTO.parteCorpoCirurgia());
                        cirurgia.setNecessidadeInternacao(procDTO.necessidadeInternacaoCirurgia() != null ? procDTO.necessidadeInternacaoCirurgia() : false);
                        cirurgia.setObservacao(procDTO.observacaoCirurgia());
                        procedimentoRealizado = cirurgia;
                        break;
                    case EXAME:
                        Exame exame = new Exame();
                        exame.setId(procDTO.id());
                        exame.setTipoProcedimento(procDTO.tipoProcedimento());
                        exame.setNomeProcedimento(procDTO.nomeProcedimento());
                        exame.setVeterinario(vetQueRealizou);
                        exame.setAnimal(animalProcedimento);
                        exame.setDataDeRealizacao(procDTO.dataRealizacao());
                        exame.setResultado(procDTO.resultadoExame());
                        exame.setObservacao(procDTO.observacaoExame());
                        procedimentoRealizado = exame;
                        break;
                    case VACINA:
                        Vacina vacina = new Vacina();
                        vacina.setId(procDTO.id());
                        vacina.setTipoProcedimento(procDTO.tipoProcedimento());
                        vacina.setNomeProcedimento(procDTO.nomeProcedimento());
                        vacina.setVeterinario(vetQueRealizou);
                        vacina.setAnimal(animalProcedimento);
                        vacina.setDataDeRealizacao(procDTO.dataRealizacao());
                        vacina.setNumeroDeDoses(procDTO.numeroDeDosesVacina() != null ? procDTO.numeroDeDosesVacina() : 0);
                        vacina.setNumeroAplicacao(procDTO.numeroAplicacaoVacina() != null ? procDTO.numeroAplicacaoVacina() : 0);
                        try {
                            vacina.setTipoAplicacao(procDTO.tipoAplicacaoVacina() != null ? VacinaTipoAplicacao.valueOf(procDTO.tipoAplicacaoVacina().toUpperCase()) : null);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Aviso (ControladorAtendimento): Tipo de aplicação de vacina inválido para '" + procDTO.nomeProcedimento() + "'. Setado como nulo.");
                            vacina.setTipoAplicacao(null);
                        }
                        vacina.setValidade(procDTO.validadeVacina());
                        procedimentoRealizado = vacina;
                        break;
                    default:
                        System.err.println("Aviso (ControladorAtendimento): Tipo de procedimento não reconhecido para mapeamento: " + procDTO.tipoProcedimento() + ". Procedimento ignorado.");
                        break;
                }
                if (procedimentoRealizado != null) {
                    listaDeProcedimentosRealizados.add(procedimentoRealizado);
                }
            }
        }

        Atendimento atendimento = atendimentoRequisicaoDTO.paraEntidade(agendamento);
        atendimento.setProcedimentosRealizados(listaDeProcedimentosRealizados);

        if (atendimento.getId() == null) {
            atendimento.setId(nextAtendimentoId++);
        }

        if (atendimento.getAgendamento() == null || atendimento.getDataRealizacao() == null) {
            System.err.println("Erro (ControladorAtendimento): Atendimento ou dados essenciais incompletos.");
            return;
        }

        repositorio.salvar(atendimento);
        System.out.println("Atendimento salvo no repositório: ID do Atendimento: " + atendimento.getId() + " para ID do Agendamento: " + agendamento.getId());
    }

    public Atendimento buscarAtendimento(Long id) {
        return repositorio.buscar(id);
    }

    public void atualizarAtendimento(Long id, AtendimentoRequisicaoDTO atendimentoAtualizadoDTO) {
        Atendimento atendimentoExistente = repositorio.buscar(id);
        if (atendimentoExistente == null) {
            System.err.println("Erro (ControladorAtendimento): Atendimento com ID " + id + " não encontrado para atualização.");
            return;
        }

        Agendamento novoAgendamento = controladorAgendamento.buscarAgendamentoPorId(atendimentoAtualizadoDTO.agendamentoID());
        if (novoAgendamento == null) {
            System.err.println("Erro (ControladorAtendimento): Novo Agendamento com ID " + atendimentoAtualizadoDTO.agendamentoID() + " não encontrado para atualização do atendimento.");
            return;
        }

        List<ProcedimentoRealizado> novaListaDeProcedimentosRealizados = new ArrayList<>();
        if (atendimentoAtualizadoDTO.procedimentosRealizados() != null) {
            for (ProcedimentoRealizadoRequisicaoDTO procDTO : atendimentoAtualizadoDTO.procedimentosRealizados()) {
                Veterinario vetQueRealizou = controladorVeterinario.buscarVeterinarioPorCrmv(procDTO.veterinarioCrmv());
                Animal animalProcedimento = controladorAnimal.buscarAnimalPorId(procDTO.animalId());

                if (vetQueRealizou == null || animalProcedimento == null) {
                    System.err.println("Aviso (ControladorAtendimento): Veterinário ou Animal para o procedimento '" + procDTO.nomeProcedimento() + "' não encontrado. Procedimento ignorado na atualização.");
                    continue;
                }

                ProcedimentoRealizado procedimentoRealizado = null;
                switch (procDTO.tipoProcedimento()) {
                    case CIRURGIA:
                        Cirurgia cirurgia = new Cirurgia();
                        cirurgia.setId(procDTO.id());
                        cirurgia.setTipoProcedimento(procDTO.tipoProcedimento());
                        cirurgia.setNomeProcedimento(procDTO.nomeProcedimento());
                        cirurgia.setVeterinario(vetQueRealizou);
                        cirurgia.setAnimal(animalProcedimento);
                        cirurgia.setDataDeRealizacao(procDTO.dataRealizacao());
                        cirurgia.setParteCorpo(procDTO.parteCorpoCirurgia());
                        cirurgia.setNecessidadeInternacao(procDTO.necessidadeInternacaoCirurgia() != null ? procDTO.necessidadeInternacaoCirurgia() : false);
                        cirurgia.setObservacao(procDTO.observacaoCirurgia());
                        procedimentoRealizado = cirurgia;
                        break;
                    case EXAME:
                        Exame exame = new Exame();
                        exame.setId(procDTO.id());
                        exame.setTipoProcedimento(procDTO.tipoProcedimento());
                        exame.setNomeProcedimento(procDTO.nomeProcedimento());
                        exame.setVeterinario(vetQueRealizou);
                        exame.setAnimal(animalProcedimento);
                        exame.setDataDeRealizacao(procDTO.dataRealizacao());
                        exame.setResultado(procDTO.resultadoExame());
                        exame.setObservacao(procDTO.observacaoExame());
                        procedimentoRealizado = exame;
                        break;
                    case VACINA:
                        Vacina vacina = new Vacina();
                        vacina.setId(procDTO.id());
                        vacina.setTipoProcedimento(procDTO.tipoProcedimento());
                        vacina.setNomeProcedimento(procDTO.nomeProcedimento());
                        vacina.setVeterinario(vetQueRealizou);
                        vacina.setAnimal(animalProcedimento);
                        vacina.setDataDeRealizacao(procDTO.dataRealizacao());
                        vacina.setNumeroDeDoses(procDTO.numeroDeDosesVacina() != null ? procDTO.numeroDeDosesVacina() : 0);
                        vacina.setNumeroAplicacao(procDTO.numeroAplicacaoVacina() != null ? procDTO.numeroAplicacaoVacina() : 0);
                        try {
                            vacina.setTipoAplicacao(procDTO.tipoAplicacaoVacina() != null ? VacinaTipoAplicacao.valueOf(procDTO.tipoAplicacaoVacina().toUpperCase()) : null);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Aviso (ControladorAtendimento): Tipo de aplicação de vacina inválido para '" + procDTO.nomeProcedimento() + "'. Setado como nulo na atualização.");
                            vacina.setTipoAplicacao(null);
                        }
                        vacina.setValidade(procDTO.validadeVacina());
                        procedimentoRealizado = vacina;
                        break;
                    default:
                        System.err.println("Aviso (ControladorAtendimento): Tipo de procedimento não reconhecido na atualização: " + procDTO.tipoProcedimento() + ". Procedimento ignorado.");
                        break;
                }
                if (procedimentoRealizado != null) {
                    novaListaDeProcedimentosRealizados.add(procedimentoRealizado);
                }
            }
        }

        atendimentoExistente.setAgendamento(novoAgendamento);
        atendimentoExistente.setDataRealizacao(atendimentoAtualizadoDTO.dataRealizacao());
        atendimentoExistente.setProcedimentosRealizados(novaListaDeProcedimentosRealizados);

        repositorio.atualizar(id, atendimentoExistente);
        System.out.println("Atendimento ID " + id + " atualizado.");
    }

    public void removerAtendimento(Long id) {
        repositorio.remover(id);
    }
}
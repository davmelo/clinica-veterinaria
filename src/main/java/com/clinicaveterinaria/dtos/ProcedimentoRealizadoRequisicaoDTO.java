package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.procedimento.ProcedimentoTipo;
import java.time.LocalDate;

public record ProcedimentoRealizadoRequisicaoDTO(
        Long id,
        ProcedimentoTipo tipoProcedimento,
        String nomeProcedimento,
        String veterinarioCrmv,
        Long animalId,
        LocalDate dataRealizacao,

        String resultadoExame,
        String observacaoExame,

        String parteCorpoCirurgia,
        Boolean necessidadeInternacaoCirurgia,
        String observacaoCirurgia,

        Integer numeroDeDosesVacina,
        Integer numeroAplicacaoVacina,
        String tipoAplicacaoVacina,
        LocalDate validadeVacina
) {}
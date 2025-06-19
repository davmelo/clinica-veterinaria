package com.clinicaveterinaria.negocio.entidades.procedimento;

import com.clinicaveterinaria.negocio.entidades.Animal;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class SolicitacaoProcedimentos {
    private Long id;
    private String nomeVetSolicitante;
    private String crmvVetSolicitante;
    private Animal animal;
    private List<ProcedimentoAgendado> procedimentos;
    private LocalDate dataSolicitacao;
    private String observacao;
}

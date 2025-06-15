package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.negocio.entidades.procedimento.ProcedimentoRealizado;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Atendimento {
    private Long id;
    private Agendamento agendamento;
    private List<ProcedimentoRealizado> procedimentosRealizados;
    private LocalDateTime dataRealizacao;
}

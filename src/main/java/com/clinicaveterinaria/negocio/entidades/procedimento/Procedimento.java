package com.clinicaveterinaria.negocio.entidades.procedimento;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"nomeProcedimento"})
public abstract class Procedimento {
    protected Long id;
    protected ProcedimentoTipo tipoProcedimento;
    protected String nomeProcedimento;
}

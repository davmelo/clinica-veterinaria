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
@EqualsAndHashCode(callSuper = true)
public class Exame extends ProcedimentoRealizado {
    private String resultado;
    private String observacao;
}

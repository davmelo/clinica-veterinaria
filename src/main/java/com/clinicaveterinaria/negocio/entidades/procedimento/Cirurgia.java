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
public class Cirurgia extends ProcedimentoRealizado {
    private String parteCorpo;
    private boolean necessidadeInternacao;
    private String observacao;
}

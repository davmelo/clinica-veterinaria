package com.clinicaveterinaria.negocio.entidades.procedimento;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Vacina extends ProcedimentoRealizado {
    private int numeroDeDoses;
    private int numeroAplicacao;
    private VacinaTipoAplicacao tipoAplicacao;
    private LocalDate validade;
}

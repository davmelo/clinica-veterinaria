package com.clinicaveterinaria.negocio.entidades.procedimento;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProcedimentoAgendado extends Procedimento{

}

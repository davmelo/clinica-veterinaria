package com.clinicaveterinaria.negocio.entidades.procedimento;

import com.clinicaveterinaria.negocio.entidades.Animal;
import com.clinicaveterinaria.negocio.entidades.Veterinario;

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
public class ProcedimentoRealizado extends Procedimento{
    protected LocalDate dataDeRealizacao;
    protected Veterinario veterinario;
    protected Animal animal;
}

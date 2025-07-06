package com.clinicaveterinaria.dtos;

import com.clinicaveterinaria.negocio.entidades.DiaSemana;
import com.clinicaveterinaria.negocio.entidades.DisponibilidadeAgenda;

import java.time.LocalTime;

public record DispoAgendaRequisicaoDTO(DiaSemana dia, LocalTime horario) {

    public DisponibilidadeAgenda paraEntidade() {
       DisponibilidadeAgenda agenda = new DisponibilidadeAgenda();
       agenda.adicionarHorario(dia, horario);
       return agenda;
    }
}

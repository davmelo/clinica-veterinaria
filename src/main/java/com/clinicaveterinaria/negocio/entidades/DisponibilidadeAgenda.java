package com.clinicaveterinaria.negocio.entidades;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

public class DisponibilidadeAgenda implements Serializable {

    private Map<DiaSemana, List<LocalTime>> horariosDisponiveis;

    public DisponibilidadeAgenda() {
        this.horariosDisponiveis = new EnumMap<>(DiaSemana.class);
    }

    // Adiciona um horário disponível a um dia específico
    public void adicionarHorario(DiaSemana dia, LocalTime horario) {
        horariosDisponiveis.computeIfAbsent(dia, k -> new ArrayList<>()).add(horario);
    }

    // Remove um horário de um dia específico
    public void removerHorario(DiaSemana dia, LocalTime horario) {
        List<LocalTime> horarios = horariosDisponiveis.get(dia);
        if (horarios != null) {
            horarios.remove(horario);
        }
    }

    // Retorna os horários disponíveis de um dia
    public List<LocalTime> getHorariosPorDia(DiaSemana dia) {
        return horariosDisponiveis.getOrDefault(dia, Collections.emptyList());
    }

    // Retorna o mapa completo (útil para iterar externamente)
    public Map<DiaSemana, List<LocalTime>> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    // Verifica se um horário específico está disponível
    public boolean estaDisponivel(DiaSemana dia, LocalTime horario) {
        return getHorariosPorDia(dia).contains(horario);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Disponibilidade da Semana:\n");
        for (Map.Entry<DiaSemana, List<LocalTime>> entry : horariosDisponiveis.entrySet()) {
            sb.append(entry.getKey()).append(": ");
            for (LocalTime hora : entry.getValue()) {
                sb.append(hora).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
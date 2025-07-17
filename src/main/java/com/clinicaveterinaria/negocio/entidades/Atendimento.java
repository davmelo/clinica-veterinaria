package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.AtendimentoRespostaDTO;
import com.clinicaveterinaria.negocio.entidades.procedimento.ProcedimentoRealizado;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Atendimento implements Serializable {
    private Long id;
    private Agendamento agendamento;
    private List<ProcedimentoRealizado> procedimentosRealizados;
    private LocalDateTime dataRealizacao;

    public Atendimento(Long id, Agendamento agendamento, LocalDateTime dataRealizacao) {
        this.id = id;
        this.agendamento = agendamento;
        this.dataRealizacao = dataRealizacao;
    }

    public AtendimentoRespostaDTO paraDTO() {
        return new AtendimentoRespostaDTO(
                agendamento.getCliente().getNome(),
                agendamento.getAnimal().getNome(),
                agendamento.getVeterinario().getNome(),
                dataRealizacao);
    }
}

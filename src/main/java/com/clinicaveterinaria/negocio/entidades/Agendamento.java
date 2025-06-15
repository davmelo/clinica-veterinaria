package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.negocio.entidades.procedimento.SolicitacaoProcedimento;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Agendamento {
    private Long id;
    private Cliente cliente;
    private Animal animal;
    private Veterinario veterinario;
    private LocalDateTime data;
    private String obsevacao;
    // SOLICITAÇÃO DE PROCEDIMENTOS?
    // REFERÊNCIA PARA AGENDA?
    private AgendamentoStatus status;

    public void remarcarConsulta(LocalDateTime novaData, String motivoRemarcacao) {
        this.setData(novaData);
    }

    public void cancelarConsulta(String motivoCancelamento) {
        this.setObsevacao(motivoCancelamento);
        this.setStatus(AgendamentoStatus.CANCELADO);
    }
}

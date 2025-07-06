package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.AgendamentoRespostaDTO;
import com.clinicaveterinaria.negocio.entidades.procedimento.SolicitacaoProcedimentos;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
    private LocalDateTime dataAgendamento;
    private String obsevacao;
    private SolicitacaoProcedimentos procedimentosSolicitados;
    private AgendamentoStatus status;

    public Agendamento(Long id, Cliente cliente, Animal animal, Veterinario veterinario, LocalDateTime dataAgendamento, String obsevacao, AgendamentoStatus status) {
        this.id = id;
        this.cliente = cliente;
        this.animal = animal;
        this.veterinario = veterinario;
        this.dataAgendamento = dataAgendamento;
        this.obsevacao = obsevacao;
        this.status = status;
    }

    public void remarcar(LocalDateTime novaData, String motivoRemarcacao) {
        this.setDataAgendamento(novaData);
    }

    public void cancelar(String motivoCancelamento) {
        this.setObsevacao(motivoCancelamento);
        this.setStatus(AgendamentoStatus.CANCELADO);
    }

    public AgendamentoRespostaDTO paraDTO() {
        return new AgendamentoRespostaDTO(cliente.getNome(), animal.getNome(), veterinario.getNome(), dataAgendamento, status);
    }
}

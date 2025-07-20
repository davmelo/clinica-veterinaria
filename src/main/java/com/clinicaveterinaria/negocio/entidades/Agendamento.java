package com.clinicaveterinaria.negocio.entidades;

import com.clinicaveterinaria.dtos.AgendamentoRespostaDTO;
import com.clinicaveterinaria.negocio.entidades.procedimento.SolicitacaoProcedimentos;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Agendamento implements Serializable {
    private Long id;
    private Cliente cliente;
    private Animal animal;
    private Veterinario veterinario;
    private LocalDateTime dataAgendamento;
    private String obsevacao;
    private SolicitacaoProcedimentos procedimentosSolicitados;
    private AgendamentoStatus status;
    private String tipoProcedimentoAgendado;

    public Agendamento(
            Long id, Cliente cliente, Animal animal,
            Veterinario veterinario, LocalDateTime dataAgendamento,
            String obsevacao, AgendamentoStatus status, String tipoProcedimentoAgendado)
    {
        this.id = id;
        this.cliente = cliente;
        this.animal = animal;
        this.veterinario = veterinario;
        this.dataAgendamento = dataAgendamento;
        this.obsevacao = obsevacao;
        this.status = status;
        this.tipoProcedimentoAgendado = tipoProcedimentoAgendado;
    }

    public void remarcar(LocalDateTime novaData, String motivoRemarcacao) {
        this.setDataAgendamento(novaData);
    }

    public void cancelar(String motivoCancelamento) {
        this.setObsevacao(motivoCancelamento);
        this.setStatus(AgendamentoStatus.CANCELADO);
    }

    public AgendamentoRespostaDTO paraDTO() {
        String tipoParaDTO = (this.tipoProcedimentoAgendado != null && !this.tipoProcedimentoAgendado.isEmpty()) ? this.tipoProcedimentoAgendado : "Não informado";
        return new AgendamentoRespostaDTO(id, cliente.getNome(), animal.getNome(), veterinario.getNome(), dataAgendamento, status, tipoParaDTO , obsevacao);
    }
}

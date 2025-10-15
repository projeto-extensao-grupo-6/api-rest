package com.project.extension.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private TipoAgendamento tipoAgendamento;
    private LocalDateTime dataAgendamento;
    private StatusAgendamento statusAgendamento;
    private String observacao;

    public Agendamento(TipoAgendamento tipoAgendamento, LocalDateTime dataAgendamento, StatusAgendamento statusAgendamento, String observacao) {
        this.tipoAgendamento = tipoAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.statusAgendamento = statusAgendamento;
        this.observacao = observacao;
    }
}

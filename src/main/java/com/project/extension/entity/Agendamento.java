package com.project.extension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    private Endereco endereco;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "agendamento_funcionario",
            joinColumns = @JoinColumn(name = "id_agendamento"),
            inverseJoinColumns = @JoinColumn(name = "id_funcionario")
    )
    private List<Funcionario> funcionarios;

    public Agendamento(TipoAgendamento tipoAgendamento, LocalDateTime dataAgendamento, StatusAgendamento statusAgendamento, String observacao) {
        this.tipoAgendamento = tipoAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.statusAgendamento = statusAgendamento;
        this.observacao = observacao;
    }
}

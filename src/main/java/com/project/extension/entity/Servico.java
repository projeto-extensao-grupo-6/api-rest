package com.project.extension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate data;
    private LocalTime horario;

    private TipoServico tipoServico;
    private String descricao;

    private TipoVidro tipoVidro;
    private List<TipoMaterialAuxiliar> tipoMaterialAuxiliares;

    @ManyToOne
    private Endereco endereco;
}

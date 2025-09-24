package com.project.extension.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataEntrada;
    private String nome;
    private String categoria;
    private Double dimensao;
    private Double espessura;
    private Integer qtdDisponivel;
    private String unidadeMedida;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;  // agora controlada por ENUM

    @Enumerated(EnumType.STRING)
    private TipoVidro tipoVidro;

    @Enumerated(EnumType.STRING)
    private TipoMaterialAuxiliar tipoMaterialAuxiliar;

}

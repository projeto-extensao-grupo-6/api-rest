package com.project.extension.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Metrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "nivel_minimo")
    private Integer nivelMinimo;

    @Column(name = "nivel_minimo")
    private Integer nivelMaximo;
}

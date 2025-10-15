package com.project.extension.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String telefone;
    private String funcao;
    private String contrato;
    private Boolean ativo;

    public Funcionario(String nome, String telefone, String funcao, String contrato, Boolean ativo) {
        this.nome = nome;
        this.telefone = telefone;
        this.funcao = funcao;
        this.contrato = contrato;
        this.ativo = ativo;
    }
}

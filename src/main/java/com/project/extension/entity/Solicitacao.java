package com.project.extension.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String cpf;
    private String cargo;
    private Status status;
}

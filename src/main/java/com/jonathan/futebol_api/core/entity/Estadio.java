package com.jonathan.futebol_api.core.entity;


import jakarta.persistence.*;
import lombok.experimental.NonFinal;


@Entity
@Table(name = "estadio")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadio;
    @NonFinal
    @Column(name = "nome")
    private String nome;


    public Long getIdEstadio() {
        return idEstadio;
    }

    public void setIdEstadio(Long idEstadio) {
        this.idEstadio = idEstadio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

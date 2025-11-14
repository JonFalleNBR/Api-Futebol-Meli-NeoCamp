package com.jonathan.futebol_api.core.entity;


import jakarta.persistence.*;
import lombok.experimental.NonFinal;


@Entity
@Table(name = "ESTADIO")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadio;
    @NonFinal
    @Column(name = "nome")
    private String nome;

    
    public int getIdEstadio(){
        return  idEstadio;
    }

    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

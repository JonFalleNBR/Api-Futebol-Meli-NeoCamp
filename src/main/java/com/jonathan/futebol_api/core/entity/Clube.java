package com.jonathan.futebol_api.core.entity;

import com.jonathan.futebol_api.core.converter.BooleanIntegerConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "Clube")
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClube;

    @Column(name = "nome")
    private String nome;

    @Column(name = "estado")
    private String estado;

    @Column(name = "ativo")
    @Convert(converter = BooleanIntegerConverter.class)
    private Boolean ativo;

    @Column(name = "fk_idestadio")
    private Integer fk_estadio; // Adicionado conforme seu DTO

    @Column(name = "vitorias")
    private Integer vitorias;

    @Column(name = "empates")
    private Integer empates;

    @Column(name = "derrotas")
    private Integer derrotas;


    // Getters e Setters
    public int getIdClube() {
        return idClube;
    }

    public void setIdClube(int idClube) {
        this.idClube = idClube;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getFk_estadio() {
        return fk_estadio;
    }

    public void setFk_estadio(Integer fk_estadio) {
        this.fk_estadio = fk_estadio;


    }

    public Integer getVitorias() {
        return vitorias;
    }

    public void setVitorias(Integer vitorias) {
        this.vitorias = vitorias;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer empates) {
        this.empates = empates;
    }

    public Integer getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }
}
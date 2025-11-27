package com.jonathan.futebol_api.core.entity;

import com.jonathan.futebol_api.core.converter.BooleanIntegerConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "clube")
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClube;

    @Column(name = "nome")
    private String nome;

    @Column(name = "estado")
    private String estado;

    @Column(name = "ativo")
    @Convert(converter = BooleanIntegerConverter.class)
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "fk_idestadio")
    private Estadio estadio; // Adicionado conforme o que conta no DTO

    @Column(name = "vitorias")
    private Integer vitorias;

    @Column(name = "empates")
    private Integer empates;

    @Column(name = "derrotas")
    private Integer derrotas;


    // Getters e Setters


    public Long getIdClube() {
        return idClube;
    }

    public void setIdClube(Long idClube) {
        this.idClube = idClube;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
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
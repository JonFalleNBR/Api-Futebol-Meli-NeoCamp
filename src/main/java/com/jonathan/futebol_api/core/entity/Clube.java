package com.jonathan.futebol_api.core.entity;


import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Entity
@Table(name = "CLUBE")
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClube;

    @NotNull
    @Column(name = "nome")
    private String nome;


    @Column(name = "data_criacao")
    private LocalDate data_criacao;

    @Column(name = "fk_idestadio")
    private Integer fkIdestadio;

    @Column(name = "vitorias")
    private Integer vitorias;

    @Column(name = "derrotas")
    private Integer derrotas;

    @Column(name = "empates")
    private Integer empates;

    @Column(name = "ativo")
    private Boolean ativo;



    public Integer getIdClube() {
        return idClube;
    }

    public void setIdClube(Integer idClube) {
        this.idClube = idClube;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Integer getFkIdestadio() {
        return fkIdestadio;
    }

    public void setFkIdestadio(Integer fkIdestadio) {
        this.fkIdestadio = fkIdestadio;
    }

    public Integer getVitorias() {
        return vitorias;
    }

    public void setVitorias(Integer vitorias) {
        this.vitorias = vitorias;
    }

    public Integer getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer empates) {
        this.empates = empates;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


}




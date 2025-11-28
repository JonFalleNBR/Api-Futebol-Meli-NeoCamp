package com.jonathan.futebol_api.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tabela_campeonato")
public class TabelaCampeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id da linha da tabela

    @OneToOne
    @JoinColumn(name = "clube_id", nullable = false, unique = true)
    private Clube clube;

    @Column(nullable = false)
    private Integer jogos = 0;

    @Column(nullable = false)
    private Integer vitorias = 0;

    @Column(nullable = false)
    private Integer empates = 0;

    @Column(nullable = false)
    private Integer derrotas = 0;

    @Column(name = "gols_marcados", nullable = false)
    private Integer golsMarcados = 0;

    @Column(name = "gols_sofridos", nullable = false)
    private Integer golsSofridos = 0;

    @Column(name = "saldo_gols", nullable = false)
    private Integer saldoGols = 0;

    @Column(nullable = false)
    private Integer pontos = 0;

    // getters e setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Clube getClube() { return clube; }

    public void setClube(Clube clube) { this.clube = clube; }

    public Integer getJogos() { return jogos; }

    public void setJogos(Integer jogos) { this.jogos = jogos; }

    public Integer getVitorias() { return vitorias; }

    public void setVitorias(Integer vitorias) { this.vitorias = vitorias; }

    public Integer getEmpates() { return empates; }

    public void setEmpates(Integer empates) { this.empates = empates; }

    public Integer getDerrotas() { return derrotas; }

    public void setDerrotas(Integer derrotas) { this.derrotas = derrotas; }

    public Integer getGolsMarcados() { return golsMarcados; }

    public void setGolsMarcados(Integer golsMarcados) { this.golsMarcados = golsMarcados; }

    public Integer getGolsSofridos() { return golsSofridos; }

    public void setGolsSofridos(Integer golsSofridos) { this.golsSofridos = golsSofridos; }

    public Integer getSaldoGols() { return saldoGols; }

    public void setSaldoGols(Integer saldoGols) { this.saldoGols = saldoGols; }

    public Integer getPontos() { return pontos; }

    public void setPontos(Integer pontos) { this.pontos = pontos; }
}

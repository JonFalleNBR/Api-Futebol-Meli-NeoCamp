package com.jonathan.futebol_api.core.usercase.service;


import com.jonathan.futebol_api.adapter.dto.TabelaCampeonatoDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.adapter.repository.TabelaCampeonatoRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.entity.TabelaCampeonato;
import com.jonathan.futebol_api.utils.CriterioDesempate;
import com.jonathan.futebol_api.utils.TipoAtuacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CampeonatoService {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TabelaCampeonatoRepository  tabelaCampeonatoRepository;

    public List<TabelaCampeonatoDTO> gerarTabelaCampeonato(
            TipoAtuacao tipoAtuacao, CriterioDesempate criterioDesempate
    ){


        tabelaCampeonatoRepository.deleteAllInBatch(); // nova logica para garantir a exclusao dos dados conforme a tabela for sendo atualizada

        List<Clube> clubes = clubeRepository.findAll();
        List<TabelaCampeonato> linhas = new ArrayList<>();

        // foreach de array de objetos
        for(Clube clube : clubes) {
            Long idCLube = clube.getIdClube();
            List<Partida> partidas = partidaRepository.findAllByClubeList(idCLube);

            if (partidas.isEmpty()) {
                continue;
            }


            int jogos = 0;
            int vitorias = 0;
            int empates = 0;
            int derrotas = 0;
            int golsMarcados = 0;
            int golsSofridos = 0;


            for (Partida p : partidas) {
//                jogos++;

                boolean mandante = p.getClubeMandante().getIdClube().equals(idCLube);

                if(tipoAtuacao == TipoAtuacao.MANDANTE && !mandante){
                    continue;
                }
                if(tipoAtuacao == TipoAtuacao.VISITANTE && mandante){
                    continue;
                }
                jogos++;


                int golsPro = mandante ? p.getGolsMandante() : p.getGolsVisitante();
                int golsContra = mandante ? p.getGolsVisitante() : p.getGolsMandante();

                golsMarcados += golsPro;
                golsSofridos += golsContra;

                if (golsPro > golsContra) {
                    vitorias++;

                } else if (golsPro == golsContra) {
                    empates++;
                } else {
                    derrotas++;
                }

            }

            if(jogos == 0){
                continue;
            }


            int saldoGols = golsMarcados - golsSofridos;
            int pontos = vitorias * 3 + empates;

/// A PARTIR DAQUI ESTA SENDO FEITA A ORGAIZACA DOS DADOS NA TABELA E A ESCOLHA


            TabelaCampeonato dadosInline = new TabelaCampeonato();
            dadosInline.setClube(clube);
            dadosInline.setPontos(pontos);
            dadosInline.setJogos(jogos);
            dadosInline.setVitorias(vitorias);
            dadosInline.setEmpates(empates);
            dadosInline.setDerrotas(derrotas);
            dadosInline.setGolsMarcados(golsMarcados);
            dadosInline.setGolsSofridos(golsSofridos);
            dadosInline.setSaldoGols(saldoGols);

            linhas.add(dadosInline);




        }

        tabelaCampeonatoRepository.saveAll(linhas);

//       List<TabelaCampeonato> ordenada = tabelaCampeonatoRepository.findAllByOrderByPontosDescSaldoGolsDescGolsMarcadosDescClube_NomeAsc();


       List<TabelaCampeonatoDTO> tabelaDTO = new ArrayList<>();

        for (TabelaCampeonato t : linhas){
            tabelaDTO.add(
                    new TabelaCampeonatoDTO(
                            t.getClube().getIdClube(),
                            t.getClube().getNome(),
                            t.getPontos(),
                            t.getJogos(),
                            t.getVitorias(),
                            t.getEmpates(),
                            t.getDerrotas(),
                            t.getGolsMarcados(),
                            t.getGolsSofridos(),
                            t.getSaldoGols()
                    ));


        }

        Comparator<TabelaCampeonatoDTO> comparator;

        switch (criterioDesempate){
            case JOGOS -> comparator = Comparator.comparing(TabelaCampeonatoDTO::jogos);
            case VITORIAS -> comparator = Comparator.comparing(TabelaCampeonatoDTO::vitorias);
            case GOLS -> comparator = Comparator.comparing(TabelaCampeonatoDTO::golsMarcados);
            case PONTOS -> {
                comparator = Comparator.comparing(TabelaCampeonatoDTO::pontos);
            }
            default -> comparator = Comparator.comparing(TabelaCampeonatoDTO::pontos);


        }

        comparator = comparator.reversed()
                .thenComparing(TabelaCampeonatoDTO::saldoGols, Comparator.reverseOrder())
                .thenComparing(TabelaCampeonatoDTO::golsMarcados, Comparator.reverseOrder())
                .thenComparing(TabelaCampeonatoDTO::nomeClube);

        tabelaDTO.sort(comparator);

        return tabelaDTO;
    }

}

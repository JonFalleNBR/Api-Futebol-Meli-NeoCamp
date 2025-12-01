package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.RetrospectoAdversarioDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoGeralDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RetrospectoService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;


    public RetrospectoGeralDTO calcularetrospectoGeral(Long idClube){

        Clube clube = clubeRepository.findById(idClube)
                .orElseThrow(()-> new Exceptions.ClubeInvalidoeException(utils.mensagensException.CLUBE_INEXISTENTE));

        List<Partida> partidas = partidaRepository.findAllByClubeList(idClube);

        if(partidas.isEmpty()){
            return  new RetrospectoGeralDTO(
                    clube.getIdClube(),
                    clube.getNome(),
                    0, // jogos
                    0, // vitórias
                    0, // empates
                    0, // derrotas
                    0, // gols marcados
                    0, // gols sofridos
                    0, // saldo
                    0// ponto

            );

        }


        int jogos = 0;
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;
        int golsMarcados = 0;
        int golsSofridos = 0;

        for(Partida p : partidas){
            jogos++;

            boolean mandante = p.getClubeMandante().getIdClube().equals(idClube);

            int golsPro = mandante ? p.getGolsMandante() : p.getGolsVisitante();
            int golsContra = mandante ? p.getGolsVisitante() : p.getGolsMandante();

            golsMarcados += golsPro;
            golsSofridos += golsContra;

            if(golsPro > golsContra){
                vitorias++;

            } else if (golsPro == golsContra) {
                empates++;

            }else {
                derrotas++;

            }

        }

        int saldoGols = golsMarcados - golsSofridos;
        int pontos = vitorias * 3 + empates;

        return new RetrospectoGeralDTO(
                clube.getIdClube(),
                clube.getNome(),
                jogos,
                vitorias,
                empates,
                derrotas,
                golsMarcados,
                golsSofridos,
                saldoGols,
                pontos
        );


    }




    public List<RetrospectoAdversarioDTO> retrospectoAdversario(Long idClubeBase) {
        Clube clubeBase = clubeRepository.findById(idClubeBase)
                .orElseThrow(() -> new Exceptions.ClubeInvalidoeException(utils.mensagensException.CLUBE_INVALIDO));

        List<Partida> partidas = partidaRepository.findAllByClubeList(idClubeBase);

        if (partidas.isEmpty()) {
            return List.of();
        }

        // HashMap
        Map<Long, AcumuladorAdversario> mapa = new HashMap<>();

        for (Partida p : partidas) {
            boolean mandante = p.getClubeMandante().getIdClube().equals(idClubeBase);

            Clube adversario = mandante ? p.getClubeVisitante() : p.getClubeMandante();
            Long idAdv = adversario.getIdClube();


            int golsPro = mandante ? p.getGolsMandante() : p.getGolsVisitante();
            int golsContra = mandante ? p.getGolsVisitante() : p.getGolsMandante();

            AcumuladorAdversario acc = mapa.computeIfAbsent(idAdv, k -> new AcumuladorAdversario(adversario.getNome()));

            acc.jogos++;
            acc.golsMarcados += golsPro;
            acc.golsSofridos += golsContra;

            if (golsPro > golsContra) {
                acc.vitorias++;
            } else if (golsPro == golsContra) {
                acc.empates++;
            } else {
                acc.derrotas++;
            }
        }

            List<RetrospectoAdversarioDTO> retrospecto = new ArrayList<>();

            // TODO o que esse for faz ?
            for (Map.Entry<Long, AcumuladorAdversario> entry : mapa.entrySet()) {
                Long idAdversario = entry.getKey();
                AcumuladorAdversario acumuladorAdversario = entry.getValue();

                int saldo = acumuladorAdversario.golsMarcados - acumuladorAdversario.golsSofridos;
                int pontos = acumuladorAdversario.vitorias * 3 + acumuladorAdversario.empates;

                double aproveitamento =
                        acumuladorAdversario.jogos > 0
                            ? (pontos / acumuladorAdversario.jogos * 3.00) * 100.00
                                : 0.00;

                retrospecto.add(new RetrospectoAdversarioDTO(
                        idAdversario,
                        acumuladorAdversario.nomeAdversario,
                        acumuladorAdversario.jogos,
                        acumuladorAdversario.vitorias,
                        acumuladorAdversario.empates,
                        acumuladorAdversario.derrotas,
                        acumuladorAdversario.golsMarcados,
                        acumuladorAdversario.golsSofridos,
                        saldo,
                        aproveitamento

                ));

            }


        // ordena: mais jogos, depois mais vitórias, depois nome do adversário
        retrospecto.sort(
                Comparator.comparing(RetrospectoAdversarioDTO::jogos).reversed()
                        .thenComparing(RetrospectoAdversarioDTO::vitorias, Comparator.reverseOrder())
                        .thenComparing(RetrospectoAdversarioDTO::nomeAdversario)
        );


            return retrospecto;


    }





    private static class AcumuladorAdversario{
        String nomeAdversario;
        int jogos = 0;
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;
        int golsMarcados = 0;
        int golsSofridos = 0;

        AcumuladorAdversario(String nomeAdversario) {
            this.nomeAdversario = nomeAdversario;
        }
    }


}

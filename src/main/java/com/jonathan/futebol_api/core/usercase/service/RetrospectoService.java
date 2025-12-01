package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoAdversariosDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoConfrontoDiretoDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoGeralDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.core.mapper.PartidaMapper;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RetrospectoService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;


    @Autowired
    private PartidaMapper partidaMapper;

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




    public List<RetrospectoAdversariosDTO> retrospectoAdversario(Long idClubeBase) {
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

            List<RetrospectoAdversariosDTO> retrospecto = new ArrayList<>();

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

                retrospecto.add(new RetrospectoAdversariosDTO(
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
                Comparator.comparing(RetrospectoAdversariosDTO::jogos).reversed()
                        .thenComparing(RetrospectoAdversariosDTO::vitorias, Comparator.reverseOrder())
                        .thenComparing(RetrospectoAdversariosDTO::nomeAdversario)
        );


            return retrospecto;


    }



    // CONFRONTO DIRETO X1
    public RetrospectoConfrontoDiretoDTO retrospectoConfrontoDireto(Long idClube1, Long idClube2) {

        Clube clube1 = clubeRepository.findById(idClube1)
                .orElseThrow(() -> new Exceptions.ClubeInvalidoeException(utils.mensagensException.CLUBE_INEXISTENTE));

        Clube clube2 = clubeRepository.findById(idClube2)
                .orElseThrow(() -> new Exceptions.ClubeInvalidoeException(utils.mensagensException.CLUBE_INEXISTENTE));

        // pega TODAS as partidas entre os dois (sem paginação aqui)
        List<Partida> partidas = partidaRepository
                .findConfrontosDiretos(idClube1, idClube2, Pageable.unpaged())
                .getContent();

        int jogos = partidas.size();

        int vitoriasClube1 = 0;
        int vitoriasClube2 = 0;
        int empates = 0;
        int golsClube1 = 0;
        int golsClube2 = 0;

        for (Partida p : partidas) {

            boolean clube1Mandante = p.getClubeMandante().getIdClube().equals(idClube1);

            int golsMandante = p.getGolsMandante();
            int golsVisitante = p.getGolsVisitante();

            int golsC1 = clube1Mandante ? golsMandante : golsVisitante;
            int golsC2 = clube1Mandante ? golsVisitante : golsMandante;

            golsClube1 += golsC1;
            golsClube2 += golsC2;

            if (golsC1 > golsC2) {
                vitoriasClube1++;
            } else if (golsC1 < golsC2) {
                vitoriasClube2++;
            } else {
                empates++;
            }
        }

        int saldoClube1 = golsClube1 - golsClube2;
        int saldoClube2 = golsClube2 - golsClube1;

        int pontosClube1 = vitoriasClube1 * 3 + empates;
        int pontosClube2 = vitoriasClube2 * 3 + empates;

        double aproveitamentoClube1 = jogos == 0 ? 0.0 : (pontosClube1 / (jogos * 3.0)) * 100.0;
        double aproveitamentoClube2 = jogos == 0 ? 0.0 : (pontosClube2 / (jogos * 3.0)) * 100.0;



        List<PartidaResponseDTO> partidasDTO = partidas.stream()
                .map(partidaMapper::toResponseDTO)
                .toList();

        return new RetrospectoConfrontoDiretoDTO(
                clube1.getIdClube(),
                clube1.getNome(),
                clube2.getIdClube(),
                clube2.getNome(),
                jogos,
                vitoriasClube1,
                vitoriasClube2,
                empates,
                golsClube1,
                golsClube2,
                saldoClube1,
                saldoClube2,
                pontosClube1,
                pontosClube2,
                aproveitamentoClube1,
                aproveitamentoClube2,
                partidasDTO
        );
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

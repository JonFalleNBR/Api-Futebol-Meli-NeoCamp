package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository_;

    @Autowired
    private ClubeRepository clubeRepository_;

    @Autowired
    private EstadioRepository estadioRepository;


    public Partida cadastrarNovaPartida(Partida partida, Estadio estadio){
        Clube clubeMandante = partida.getClubeMandante();
        Clube clubeVisitante = partida.getClubeVisitante();
        Estadio estadioPartida = partida.getEstadio();


        if(!clubeRepository_.existsById(clubeMandante.getIdClube())){
            throw new RuntimeException(utils.MensagensException.CLUBE_INEXISTENTE.getMensagem());

        }
        if(!clubeRepository_.existsById(clubeVisitante.getIdClube())){
            throw new RuntimeException(utils.MensagensException.CLUBE_INEXISTENTE.getMensagem());
        }


        if (!estadioRepository.existsById(estadioPartida.getIdEstadio())){
            throw new RuntimeException(utils.MensagensException.ESTADIO_INEXISTENTE.getMensagem());

        }


        return partidaRepository_.save(partida);
    }


    public Optional<Partida> listaPartidaPorId(Long id){
        Partida partida = partidaRepository_.findById(id)
                .orElseThrow(() -> new RuntimeException(utils.MensagensException.PARTIDA_INVALIDA.getMensagem()));

        return partidaRepository_.findById(id);
    }

    public Page<Partida> listaTodasPartidas(Pageable pageable){
        return partidaRepository_.findAll(pageable);

    }

    // TODO fazer melhoria dos logs de Exception - substituir o RunTimeException



}

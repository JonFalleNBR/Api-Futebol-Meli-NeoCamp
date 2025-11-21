package com.jonathan.futebol_api.core.usercase.service;


import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository_;

    @Autowired
    private ClubeRepository clubeRepository_;

    @Autowired
    private EstadioRepository estadioRepository;


    public Partida cadastrarNovaPartida(Partida partida){

        // TODO validar se clube e estadio existem

        return partidaRepository_.save(partida);
    }

    // TODO fazer metodos de Busca por id e listar todas as partidas - nao havera delecao de partidas e nem edicao.



}

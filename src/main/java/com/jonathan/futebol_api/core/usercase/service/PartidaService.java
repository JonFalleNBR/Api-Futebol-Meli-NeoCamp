package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.core.mapper.PartidaMapper;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository_;

    @Autowired
    private ClubeRepository clubeRepository_;

    @Autowired
    private EstadioRepository estadioRepository;


    public PartidaResponseDTO cadastrarNovaPartida(PartidaRequestDTO partidaRequestDTO){

        Long idClubeMandante = partidaRequestDTO.idClubeMandante();
        Long idClubeVisitante = partidaRequestDTO.idClubeVisitante();
        Long idEstadio = partidaRequestDTO.idEstadio();

        if(!clubeRepository_.existsById(idClubeMandante)){
            throw new Exceptions.ClubeInvalidoeException(utils.MensagensException.CLUBE_INEXISTENTE);

        }
        if(!clubeRepository_.existsById(idClubeVisitante)){
            throw new Exceptions.ClubeInvalidoeException(utils.MensagensException.CLUBE_INEXISTENTE);
        }


        if (!estadioRepository.existsById(idEstadio)){
            throw new Exceptions.EstadioInexistenteException(utils.MensagensException.ESTADIO_INEXISTENTE);

        }


        Partida partida = PartidaMapper.INSTANCE.toEntity(partidaRequestDTO);

        partida = partidaRepository_.save(partida);
        return PartidaMapper.INSTANCE.toResponseDTO(partida);
    }


    public PartidaResponseDTO listaPartidaPorId(Long id){
        Partida partida = partidaRepository_.findById(id)
                .orElseThrow(() -> new Exceptions.PartidaInvalidaException(utils.MensagensException.PARTIDA_INVALIDA));

        return PartidaMapper.INSTANCE.toResponseDTO(partida);
    }

    public Page<Partida> listaTodasPartidas(Pageable pageable){
        return partidaRepository_.findAll(pageable);

    }



    public void  deletarPartidaHistorico(@PathVariable Long id){
            Partida partida = partidaRepository_.findById(id)
                    .orElseThrow(() ->new Exceptions.PartidaInvalidaException(utils.MensagensException.PARTIDA_INVALIDA));


            partidaRepository_.deleteById(id);

    }

}

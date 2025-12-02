package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.core.mapper.PartidaMapper;
import com.jonathan.futebol_api.utils.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository_;

    @Autowired
    private ClubeRepository clubeRepository_;

    @Autowired
    private EstadioRepository estadioRepository;


    @Autowired
    private PartidaMapper partidaMapper;


    public PartidaResponseDTO cadastrarNovaPartida(PartidaRequestDTO partidaRequestDTO){

        Long idClubeMandante = partidaRequestDTO.idClubeMandante();
        Long idClubeVisitante = partidaRequestDTO.idClubeVisitante();
        Long idEstadio = partidaRequestDTO.idEstadio();


        if(idClubeMandante == null || idClubeVisitante == null || idEstadio == null){
            throw  new com.jonathan.futebol_api.core.exception.Exceptions.PartidaInvalidaException(Exceptions.mensagensException.PARTIDA_INVALIDA);
        }

        if (idClubeMandante.equals(idClubeVisitante)) {
            throw new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INVALIDO);
        }

        if(!clubeRepository_.existsById(idClubeMandante)){
                throw new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INEXISTENTE);
        }

        if (!clubeRepository_.existsById(idClubeVisitante)){
            throw new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INEXISTENTE);
        }

        if (!estadioRepository.existsById(idEstadio)) {
            throw new com.jonathan.futebol_api.core.exception.Exceptions.EstadioInexistenteException(Exceptions.mensagensException.ESTADIO_INEXISTENTE);
        }



        Partida partida = partidaMapper.toEntity(partidaRequestDTO);

        // formatar o retorno do placar
        Integer gm = partida.getGolsMandante();
        Integer gv = partida.getGolsVisitante();
        partida.setResultado(gm + " x " + gv); // 2 x 1 por ex

        //TODO ajustar logica para estatisticas no futuro


        partida = partidaRepository_.save(partida);
        return partidaMapper.toResponseDTO(partida);
    }


    public PartidaResponseDTO listaPartidaPorId(Long id){
        Partida partida = partidaRepository_.findById(id)
                .orElseThrow(() -> new com.jonathan.futebol_api.core.exception.Exceptions.PartidaInvalidaException(Exceptions.mensagensException.PARTIDA_INVALIDA));

        return partidaMapper.toResponseDTO(partida);
    }

    public Page<Partida> listaTodasPartidas(Pageable pageable){
        return partidaRepository_.findAll(pageable);

    }



    public void  deletarPartidaHistorico( Long id){
            Partida partida = partidaRepository_.findById(id)
                    .orElseThrow(() ->new com.jonathan.futebol_api.core.exception.Exceptions.PartidaInvalidaException(Exceptions.mensagensException.PARTIDA_INVALIDA));


            partidaRepository_.delete(partida);

    }



    public Page<PartidaResponseDTO> listarPartidaPorClube(Long id, Pageable pageable){

     if(!clubeRepository_.existsById(id)){
         throw new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INEXISTENTE);
     }

     Page<Partida> partidasClube = partidaRepository_.findPartidaByClube(id, pageable);

     return partidasClube.map(partidaMapper::toResponseDTO);

    }


    public List<PartidaResponseDTO> listarGoleadas(){
    List<Partida> lista = partidaRepository_.findGoleadas();

    return lista.stream()
            .map(partidaMapper::toResponseDTO)
            .toList();

    }

    public Page<PartidaResponseDTO> listarPartidaPorData(LocalDateTime inicio,
                                                            LocalDateTime fim,
                                                            Pageable pageable){

        Page<Partida> lista = partidaRepository_.findPartidasBetweenDatas(inicio, fim, pageable);

        return lista.map(partidaMapper::toResponseDTO);


    }





}

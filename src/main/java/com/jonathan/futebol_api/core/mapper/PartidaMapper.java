package com.jonathan.futebol_api.core.mapper;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.core.entity.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PartidaMapper{


    // USO DE MapStruct para mapeamento mais inteligente e enxuto

    PartidaMapper INSTANCE = Mappers.getMapper(PartidaMapper.class);

    // Mapeia Partida para PartidaResponseDTo
    @Mapping(source = "clubeMandante.idClube", target = "idClubeMandante")
    @Mapping(source = "clubeVisitante.idClube", target = "idClubeVisitante")
    @Mapping(source = "estadio.idEstadio", target = "idEstadio")
    PartidaResponseDTO toResponseDTO(Partida partida);

    // Mapeia PartidaRequestDTO para Partida
    @Mapping(target = "clubeMandante.idClube", source = "idClubeMandante")
    @Mapping(target = "clubeVisitante.idClube", source = "idClubeVisitante")
    @Mapping(target = "estadio.idEstadio", source = "idEstadio")
    Partida toEntity(PartidaRequestDTO partidaRequestDTO);

}

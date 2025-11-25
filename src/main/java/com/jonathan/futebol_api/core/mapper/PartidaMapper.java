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

    @Mapping(source = "id", target = "Id")
    @Mapping(source = "clubeMandante.idClube", target = "idClubeMandante")
    @Mapping(source = "clubeVisitante.idClube", target = "idClubeVisitante")
    @Mapping(source = "estadio.idEstadio", target = "idEstadio")
    @Mapping(source = "dataHora", target = "dataHoraPartida")
    PartidaResponseDTO toResponseDTO(Partida partida);

    @Mapping(target = "clubeMandante.idClube", source = "idClubeMandante")
    @Mapping(target = "clubeVisitante.idClube", source = "idClubeVisitante")
    @Mapping(target = "estadio.idEstadio", source = "idEstadio")
    @Mapping(target = "dataHora", source = "horarioPartida")
    @Mapping(target = "resultado", ignore = true) // se ainda não for informado no request
    Partida toEntity(PartidaRequestDTO dto);

}

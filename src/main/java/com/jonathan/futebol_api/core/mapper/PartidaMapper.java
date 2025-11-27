package com.jonathan.futebol_api.core.mapper;

import com.jonathan.futebol_api.adapter.dto.PartidaRequestDTO;
import com.jonathan.futebol_api.adapter.dto.PartidaResponseDTO;
import com.jonathan.futebol_api.core.entity.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PartidaMapper{


    // USO DE MapStruct para mapeamento mais inteligente e enxuto

    PartidaMapper INSTANCE = Mappers.getMapper(PartidaMapper.class);

    // Mapeia Partida para PartidaResponseDTo

    @Mapping(source = "id",                 target = "Id") // Se atentar para o Id com I maiusculo no target
    @Mapping(source = "clubeMandante.idClube", target = "idClubeMandante")
    @Mapping(source = "clubeMandante.nome",    target = "nomeClubeMandante")
    @Mapping(source = "golsMandante",         target = "golsClubeMandante")

    @Mapping(source = "clubeVisitante.idClube", target = "idClubeVisitante")
    @Mapping(source = "clubeVisitante.nome",    target = "nomeClubeVisitante")
    @Mapping(source = "golsVisitante",          target = "golsClubeVisitante")

    @Mapping(source = "estadio.nome",      target = "nomeEstadio")
    @Mapping(source = "dataHora",          target = "dataHoraPartida")
    @Mapping(target = "dataHoraFormatada", expression = "java(formatarData(partida.getDataHora()))")
    @Mapping(source = "resultado",         target = "resultado")
    PartidaResponseDTO toResponseDTO(Partida partida);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clubeMandante.idClube",   source = "idClubeMandante")
    @Mapping(target = "clubeVisitante.idClube",  source = "idClubeVisitante")
    @Mapping(target = "estadio.idEstadio",       source = "idEstadio")
    @Mapping(target = "dataHora",                source = "horarioPartida")
    @Mapping(target = "golsMandante",            source = "golsMandante")
    @Mapping(target = "golsVisitante",           source = "golsVisitante")
    @Mapping(target = "resultado",               ignore = true) // setamos no service
    Partida toEntity(PartidaRequestDTO dto);


    // nova logica paa adicionar o helper que ira formatar a data e hora
    default String formatarData(LocalDateTime dataHora){
        if(dataHora == null) return null  ;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // esse resultado sera retornado no Response para o clinete
        return dataHora.format(formatter);

    }

}

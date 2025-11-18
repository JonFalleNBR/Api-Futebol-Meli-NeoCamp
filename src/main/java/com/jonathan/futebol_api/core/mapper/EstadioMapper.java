package com.jonathan.futebol_api.core.mapper;

import com.jonathan.futebol_api.adapter.dto.EstadioRequestDTO;
import com.jonathan.futebol_api.adapter.dto.EstadioResponseDTO;
import com.jonathan.futebol_api.core.entity.Estadio;

import java.util.List;
import java.util.stream.Collectors;

public class EstadioMapper {


    public static Estadio toEntity(EstadioRequestDTO estadioRequestDTO){
        Estadio estadio = new Estadio();
        estadio.setNome(estadioRequestDTO.nome());

        return estadio;

    }

    public static EstadioResponseDTO toDto(Estadio estadio){
            return new EstadioResponseDTO(
                    estadio.getIdEstadio(),
                    estadio.getNome()


            );
    }

    public static List<EstadioResponseDTO> estadioResponseDTOList(List<Estadio> estadios){
            return estadios.stream()
                    .map(EstadioMapper::toDto)
                    .collect(Collectors.toList());
    }

}

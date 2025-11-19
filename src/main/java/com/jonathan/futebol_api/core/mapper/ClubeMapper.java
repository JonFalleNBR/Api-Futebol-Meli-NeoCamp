package com.jonathan.futebol_api.core.mapper;

import com.jonathan.futebol_api.adapter.dto.ClubeRequestDto;
import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.core.entity.Clube;

public class ClubeMapper {

// Main Method responsible to Convert the ClubRequestDTO to Clube Entity
    public static Clube toEntity(ClubeRequestDto requestDto){
        Clube clube = new Clube();

        clube.setNome(requestDto.nome());
        clube.setFk_estadio(requestDto.idEstadio());
        clube.setEstado(requestDto.estado());
        clube.setAtivo(true);
        clube.setVitorias(requestDto.vitorias());
        clube.setEmpates(requestDto.empates());
        clube.setDerrotas(requestDto.derrotas());


        return clube;
    }

    public static ClubeResponseDTO  toResponseDto (Clube clube){
            return new ClubeResponseDTO(
                    clube.getIdClube(),
                    clube.getNome(),
                    clube.getEstado(),
                    clube.getFk_estadio().toString(),
                    clube.getVitorias(),
                    clube.getEmpates(),
                    clube.getDerrotas(),
                    clube.getAtivo()

            );
    }
}

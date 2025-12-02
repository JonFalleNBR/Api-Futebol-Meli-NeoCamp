package com.jonathan.futebol_api.core.mapper;

import com.jonathan.futebol_api.adapter.dto.ClubeRequestDto;
import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;

import java.time.LocalDate;

public class ClubeMapper {

    public static Clube toEntity(ClubeRequestDto requestDto){
        Clube clube = new Clube();

        clube.setNome(requestDto.nome());
        clube.setEstado(requestDto.estado());
        clube.setAtivo(requestDto.ativo() != null ? requestDto.ativo() : true);
        clube.setVitorias(requestDto.vitorias());
        clube.setEmpates(requestDto.empates());
        clube.setDerrotas(requestDto.derrotas());

        Estadio estadio = new Estadio();
            estadio.setIdEstadio(requestDto.idEstadio());   // usa só o ID pra JPA fazer o join
            clube.setEstadio(estadio);

        clube.setDataCriacao(LocalDate.now());


        return clube;
    }

    public static ClubeResponseDTO  toResponseDto (Clube clube){
        String nomeEstadio = clube.getEstadio() != null
                    ? clube.getEstadio().getNome() : null;

            return new ClubeResponseDTO(
                    clube.getIdClube(),
                    clube.getNome(),
                    clube.getEstado(),
                    nomeEstadio,
                    clube.getVitorias(),
                    clube.getEmpates(),
                    clube.getDerrotas(),
                    clube.getAtivo()

            );
    }
}

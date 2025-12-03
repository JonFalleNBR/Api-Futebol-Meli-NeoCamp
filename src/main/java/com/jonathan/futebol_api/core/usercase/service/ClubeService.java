package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoGeralDTO;
import com.jonathan.futebol_api.adapter.repository.PartidaRepository;
import com.jonathan.futebol_api.core.entity.Partida;
import com.jonathan.futebol_api.helper.ClubeSpecifications;
import com.jonathan.futebol_api.utils.Exceptions;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.jonathan.futebol_api.helper.ClubeSpecifications.*;


@Service
public class ClubeService {


    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private PartidaRepository partidaRepository;


    public Clube cadastrarClube(Clube clube){
        return clubeRepository.save(clube);
    }


    public Clube editarClube(Long id, Clube clubeAtualizado){
        if(!clubeRepository.existsById(id)) {
            throw  new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INEXISTENTE);

        }
        clubeAtualizado.setIdClube(id);
        return clubeRepository.save(clubeAtualizado);
    }


    public Clube inativarClube(Long id){
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INEXISTENTE));
        clube.setAtivo(false);
        return clubeRepository.save(clube);

    }

    public Optional<Clube> buscarClubePorId(Long id){
        return clubeRepository.findById(id);

    }

    public Page<Clube> listarClubes(Pageable pageabe){
        return clubeRepository.findAll(pageabe);


    }


    // TODO Refinar busca e metodos Contains e Equals
    public Page<Clube> listarClubesPorFiltragem(
            String nome,
            String estado,
            Boolean ativo,
            Pageable pageable
    ){

        Specification<Clube> spec =  Specification.where(null);

        if(nome != null && !nome.isBlank()){
            spec = spec.and(ClubeSpecifications.nomeContains(nome));


        }
        if(estado != null && !estado.isBlank()){
            spec = spec.and(estadoEquals(estado));

        }
        if(ativo != null){
            spec = spec.and(ativoEquals(ativo));

        }

        return clubeRepository.findAll(spec, pageable);


    }


    // PArte do retrospecto
    public RetrospectoGeralDTO obterRetrospectoGeral(long idClube){
        Clube clube = clubeRepository.findById(idClube)
                .orElseThrow(() -> new com.jonathan.futebol_api.core.exception.Exceptions.ClubeInvalidoeException(Exceptions.mensagensException.CLUBE_INVALIDO));

        List<Partida> partidas = partidaRepository.findAllByClubeList(idClube);

        int jogos = 0;
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;
        int golsMarcados = 0;
        int golsSofridos = 0;

        for(Partida p : partidas){

            jogos++;

            boolean mandante = p.getClubeMandante().getIdClube().equals(idClube);

            int golsPro = mandante ? p.getGolsMandante() : p.getGolsVisitante();
            int golsContra = mandante ? p.getGolsVisitante() : p.getGolsMandante();

            golsMarcados += golsPro;
            golsSofridos += golsContra;


            if(golsPro > golsContra){
                vitorias++;

            } else if (golsPro == golsContra){
                empates++;


            }else {
                derrotas++;
            }

        }


        int saldoGols = golsMarcados - golsSofridos;
        int pontos = vitorias * 3 + empates;


        return new RetrospectoGeralDTO(
                clube.getIdClube(),
                clube.getNome(),
                jogos,
                vitorias,
                empates,
                derrotas,
                golsMarcados,
                golsSofridos,
                saldoGols,
                pontos

        );

    }


}

package com.jonathan.futebol_api.core.usercase.service;

import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.utils;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClubeService {


    @Autowired
    private ClubeRepository clubeRepository;


    public Clube cadastrarClube(Clube clube){
        return clubeRepository.save(clube);
    }


    public Clube editarClube(Integer id, Clube clubeAtualizado){
        if(!clubeRepository.existsById(id)) {
            throw  new Exceptions.ClubeInvalidoeException(utils.MensagensException.CLUBE_INEXISTENTE);

        }
        clubeAtualizado.setIdClube(id);
        return clubeRepository.save(clubeAtualizado);
    }


    public Clube inativarClube(Integer id){
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new Exceptions.ClubeInvalidoeException(utils.MensagensException.CLUBE_INEXISTENTE));
        clube.setAtivo(false);
        return clubeRepository.save(clube);

    }

    public Optional<Clube> buscarClubePorId(Integer id){
        return clubeRepository.findById(id);

    }

    public Page<Clube> listarClubes(Pageable pageabe){
        return clubeRepository.findAll(pageabe);


    }


}

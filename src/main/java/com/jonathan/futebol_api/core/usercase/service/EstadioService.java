package com.jonathan.futebol_api.core.usercase.service;


import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.core.entity.Estadio;
import com.jonathan.futebol_api.core.exception.Exceptions;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public Estadio cadastrarEstadio(Estadio estadio){

        return estadioRepository.save(estadio);

    }

    public Estadio editarEstadio(Long id, Estadio estadioAtualizado){
        if(!estadioRepository.existsById(id)){
            throw new Exceptions.EstadioInexistenteException(utils.MensagensException.ESTADIO_INEXISTENTE);

        }
        estadioAtualizado.setIdEstadio(id);
        return estadioRepository.save(estadioAtualizado);


    }

    public List<Estadio> listaTodosEstadios(){
        return estadioRepository.findAll();

    }

    public Optional<Estadio> buscarEstadioPorId(Long id){
      return estadioRepository.findById(id);

    }

    public void removerEstadio(Long id){
        if(!estadioRepository.existsById(id)){
                throw new Exceptions.EstadioInexistenteException(utils.MensagensException.ESTADIO_INEXISTENTE);

        }
        estadioRepository.deleteById(id);

    }

}





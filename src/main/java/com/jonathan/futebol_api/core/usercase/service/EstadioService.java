package com.jonathan.futebol_api.core.usercase.service;


import com.jonathan.futebol_api.adapter.repository.EstadioRepository;
import com.jonathan.futebol_api.core.entity.Estadio;
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

    public Estadio editarEstadio(Integer id, Estadio estadioAtualizado){
        if(!estadioRepository.existsById(id)){
            throw new RuntimeException(utils.MensagensException.ESTADIO_INEXISTENTE.getMensagem());

        }
        estadioAtualizado.setIdEstadio(id);
        return estadioRepository.save(estadioAtualizado);


    }

    public List<Estadio> listaTodosEstadios(){
        return estadioRepository.findAll();

    }

    public Optional<Estadio> buscarEstadioPorId(Integer id){
      return estadioRepository.findById(id);

    }

    public void removerEstadio(Integer id){
        if(!estadioRepository.existsById(id)){
                throw new RuntimeException(utils.MensagensException.ESTADIO_INEXISTENTE.getMensagem());

        }
        estadioRepository.deleteById(id);

    }

}





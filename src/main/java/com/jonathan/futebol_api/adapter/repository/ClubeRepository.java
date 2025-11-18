package com.jonathan.futebol_api.adapter.repository;

import com.jonathan.futebol_api.core.entity.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClubeRepository extends JpaRepository<Clube, Integer>, JpaSpecificationExecutor<Clube> {

//Optional<Clube> findbyId(Integer id);

//Page<Clube> findBynomeContainsIgnoreCase(String nome, Pageable pageable);
//
//Page<Clube> findByAtivo(Boolean ativo, Pageable pageable);
//
//Page<Clube> findByEstado(String estado, Pageable pageable);




}

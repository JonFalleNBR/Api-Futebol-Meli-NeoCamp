package com.jonathan.futebol_api.adapter.repository;

import aj.org.objectweb.asm.Opcodes;
import com.jonathan.futebol_api.core.entity.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EstadioRepository extends JpaRepository<Estadio, Long>, JpaSpecificationExecutor<Estadio> {

    Optional<Estadio> findById(Long id);


}

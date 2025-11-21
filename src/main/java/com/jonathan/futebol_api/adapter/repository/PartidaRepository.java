package com.jonathan.futebol_api.adapter.repository;

import com.jonathan.futebol_api.core.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository  extends JpaRepository<Partida, Long> {


}

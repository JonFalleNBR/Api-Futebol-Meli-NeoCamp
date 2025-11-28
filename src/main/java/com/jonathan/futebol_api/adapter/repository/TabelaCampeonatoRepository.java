package com.jonathan.futebol_api.adapter.repository;

import com.jonathan.futebol_api.core.entity.TabelaCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TabelaCampeonatoRepository  extends JpaRepository<TabelaCampeonato, Long> {

    Optional<TabelaCampeonato> findByClube_IdClube(Long idClube);

    List<TabelaCampeonato> findAllByOrderByPontosDescSaldoGolsDescGolsMarcadosDescClube_NomeAsc(); // tabela importante de ordenacao


}

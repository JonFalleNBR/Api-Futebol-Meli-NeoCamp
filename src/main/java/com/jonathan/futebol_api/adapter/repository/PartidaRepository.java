package com.jonathan.futebol_api.adapter.repository;

import com.jonathan.futebol_api.core.entity.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PartidaRepository  extends JpaRepository<Partida, Long> {


    // TODAS AS PARIDAS ONDE O CLUBE PARTICIPOU PELO ID
    @Query("""  
            SELECT  p 
                FROM Partida p 
                    WHERE p.clubeMandante.idClube = :idClube
                        OR p.clubeVisitante.idClube = :idClube

""")
    Page<Partida> findPartidaByClube(@Param("idClube") Long idClube, Pageable pageable);



    // versao para buscar partidas simples para estatisticas
    @Query("""
    SELECT p
    FROM Partida p
    WHERE p.clubeMandante.idClube = :idClube
       OR p.clubeVisitante.idClube = :idClube
""")
    List<Partida> findAllByClubeList(@Param("idClube") Long idClube);


    // Filtra todas as partidas entre os clubes
    @Query("""
   
   SELECT  p 
    FROM Partida p 
        WHERE (p.clubeMandante.idClube = :clube1 AND p.clubeVisitante.idClube = :clube2)
              OR  (p.clubeMandante.idClube = :clube2 AND p.clubeVisitante.idClube = :clube1)
"""
    )
    Page<Partida> findConfrontosDiretos(@Param("clube1") Long clube1,
                                        @Param("clube2") Long clube2,
                                        Pageable pageable);



    // Filtra partida com intervalo de data e hora
    @Query("""
        SELECT p 
            FROM Partida p 
            WHERE p.dataHora BETWEEN  :inicio AND :fim 
                ORDER BY p.dataHora ASC 


"""
    )
    Page<Partida> findPartidasBetweenDatas(@Param("inicio") LocalDateTime inicio,
                                           @Param("fim") LocalDateTime fim,
                                           Pageable pageable);



    //filtrar goleadas
    @Query("""
        SELECT p
        FROM Partida p
        WHERE ABS(p.golsMandante - p.golsVisitante) >= 3
    """)
    List<Partida> findGoleadas();

}

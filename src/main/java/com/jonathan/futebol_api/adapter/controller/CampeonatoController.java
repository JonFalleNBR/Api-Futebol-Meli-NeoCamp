package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.dto.TabelaCampeonatoDTO;
import com.jonathan.futebol_api.core.usercase.service.CampeonatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @GetMapping("/tabela")
    private ResponseEntity<List<TabelaCampeonatoDTO>> getTabelaCompleta(){
        List<TabelaCampeonatoDTO > tabela = campeonatoService.gerarTabelaCampeonato();
        return ResponseEntity.ok(tabela);

    }




    // TODO proximas etapas Retrospecto contra adversários (busca avançada 2)
    //
    // TODO Confrontos diretos com estatística dos dois lados (busca avançada 3)

}

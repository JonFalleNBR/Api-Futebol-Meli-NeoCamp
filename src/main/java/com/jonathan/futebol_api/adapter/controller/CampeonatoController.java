package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.dto.TabelaCampeonatoDTO;
import com.jonathan.futebol_api.core.usercase.service.CampeonatoService;
import com.jonathan.futebol_api.utils.CriterioDesempate;
import com.jonathan.futebol_api.utils.TipoAtuacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @GetMapping("/tabela")
    private ResponseEntity<List<TabelaCampeonatoDTO>> getTabelaCompleta(
            @RequestParam(defaultValue = "TODOS")TipoAtuacao atuacao,
            @RequestParam(defaultValue = "PONTOS")CriterioDesempate criterioDesempate
            ){
        List<TabelaCampeonatoDTO > tabela = campeonatoService.gerarTabelaCampeonato(atuacao, criterioDesempate);
        return ResponseEntity.ok(tabela);

    }




    // TODO proximas etapas Retrospecto contra adversários (busca avançada 2) -> DONE
    //
    // TODO Confrontos diretos com estatística dos dois lados (busca avançada 3) -> DONE

}

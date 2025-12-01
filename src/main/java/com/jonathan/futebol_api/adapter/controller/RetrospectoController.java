package com.jonathan.futebol_api.adapter.controller;

import com.jonathan.futebol_api.adapter.dto.RetrospectoAdversariosDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoConfrontoDiretoDTO;
import com.jonathan.futebol_api.adapter.dto.RetrospectoGeralDTO;
import com.jonathan.futebol_api.core.usercase.service.RetrospectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clubes")
public class RetrospectoController {

    @Autowired
    RetrospectoService retrospectoService;


    @GetMapping("/{id}/retrospecto-geral")
    private ResponseEntity<RetrospectoGeralDTO> getRetrospectoGeral(@PathVariable Long id){
        RetrospectoGeralDTO dto = retrospectoService.calcularetrospectoGeral(id);
       return  ResponseEntity.ok(dto);

    }

    @GetMapping("{idClube}/retrospecto-adversarios")
    private ResponseEntity<List<RetrospectoAdversariosDTO>> getRetrospectoAdversario(@PathVariable Long idClube){
        List<RetrospectoAdversariosDTO> retrospectoAdversario = retrospectoService.retrospectoAdversario(idClube);

        return ResponseEntity.ok(retrospectoAdversario);
    }

}

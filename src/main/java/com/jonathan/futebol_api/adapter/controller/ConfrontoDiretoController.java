package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.dto.RetrospectoConfrontoDiretoDTO;
import com.jonathan.futebol_api.core.usercase.service.RetrospectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confrontos")
public class ConfrontoDiretoController {

    @Autowired
    private RetrospectoService retrospectoService;

    @GetMapping("/{idClube1}/{idClube2}")
    public ResponseEntity<RetrospectoConfrontoDiretoDTO> getConfrontoDireto(
            @PathVariable Long idClube1,
            @PathVariable Long idClube2) {

        RetrospectoConfrontoDiretoDTO dto = retrospectoService.retrospectoConfrontoDireto(idClube1, idClube2);
        return ResponseEntity.ok(dto);
    }

}

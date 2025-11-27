package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.dto.ClubeRequestDto;
import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.core.entity.Clube;
import com.jonathan.futebol_api.core.entity.Estadio;
import com.jonathan.futebol_api.core.mapper.ClubeMapper;
import com.jonathan.futebol_api.core.usercase.service.ClubeService;
import com.jonathan.futebol_api.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    @Autowired
    private ClubeService service;

    @Autowired
    private ClubeRepository clubeRepository;


    @PostMapping
    public ResponseEntity<ClubeResponseDTO> cadastrarClube(@RequestBody ClubeRequestDto request){

        Clube clube = ClubeMapper.toEntity(request); //-> Usa o mapper para converter o DTO na entidade

        Clube savedClube = service.cadastrarClube(clube); // -> Salva o clube na base de Dados

        ClubeResponseDTO clubeResponseDTO = ClubeMapper.toResponseDto(savedClube); // -> Converte a entidade retornada no DTO de response

        return ResponseEntity.status(HttpStatus.CREATED).body(clubeResponseDTO); // -> devolve os dados do DTO na requiscao do Usuario

    }


    @GetMapping("/{id}")
    public ResponseEntity<ClubeResponseDTO> buscarClubeId( @PathVariable Long id){
        Optional<Clube> clubeopt = service.buscarClubePorId(id);

        return clubeopt.map(clube -> {
            ClubeResponseDTO clubeResponseDTO = ClubeMapper.toResponseDto(clube);
            return ResponseEntity.ok(clubeResponseDTO);

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<ClubeResponseDTO>> listarClubes(Pageable pageable){
        Page<Clube> clubes = service.listarClubes(pageable);

        Page<ClubeResponseDTO> clubesDtos = clubes.map(ClubeMapper::toResponseDto);


        return ResponseEntity.ok(clubesDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubeResponseDTO> editarClube(@PathVariable Long id,  @RequestBody ClubeRequestDto clubeResquest){


        Clube clubeExistente = service.buscarClubePorId(id)
                .orElseThrow(()-> new RuntimeException(utils.mensagensException.CLUBE_INEXISTENTE.getMensagem()));

        clubeExistente.setNome(clubeResquest.nome());
        clubeExistente.setEstado(clubeResquest.estado());

        // ajuste de estadio setado o objeto diretamente
        Estadio estadio = new Estadio();
        estadio.setIdEstadio(clubeResquest.idEstadio());
        clubeExistente.setEstadio(estadio);

        // TODO ajustar para as estatisticas tambem por favor - Nao esquecer requisito Funcional Importante

        clubeExistente.setVitorias(clubeResquest.vitorias());
        clubeExistente.setEmpates(clubeResquest.empates());
        clubeExistente.setDerrotas(clubeResquest.derrotas());



        Clube updatedCLube = service.editarClube(id, clubeExistente);

         ClubeResponseDTO clubeResponseDTO = ClubeMapper.toResponseDto(updatedCLube);


        return ResponseEntity.ok(clubeResponseDTO);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarClube(@PathVariable Long id){
        if(!clubeRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); //
        }
        service.inativarClube(id);

        return ResponseEntity.noContent().build();

    }

    // TODO -> melhorar os logs de http nas exceptions


}

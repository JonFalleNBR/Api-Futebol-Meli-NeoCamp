package com.jonathan.futebol_api.adapter.controller;


import com.jonathan.futebol_api.adapter.dto.ClubeRequestDto;
import com.jonathan.futebol_api.adapter.dto.ClubeResponseDTO;
import com.jonathan.futebol_api.adapter.repository.ClubeRepository;
import com.jonathan.futebol_api.core.entity.Clube;
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
        Clube clube = new Clube();
        clube.setNome(request.nome());
        clube.setFk_estadio(request.idEstadio());
        clube.setEstado(request.estado());
        clube.setAtivo(true);
        clube.setVitorias(0);
        clube.setDerrotas(0);
        clube.setEmpates(0);


        Clube saveClube = service.cadastrarClube(clube);

        ClubeResponseDTO responseDTO = new ClubeResponseDTO(
                saveClube.getNome(),
                saveClube.getEstado(),
                saveClube.getFk_estadio().toString(),
                saveClube.getVitorias(),
                saveClube.getEmpates(),
                saveClube.getDerrotas(),
                saveClube.getAtivo()

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);


    }


    @GetMapping("/{id}")
    public ResponseEntity<ClubeResponseDTO> buscarClube( @PathVariable Integer id){
        Optional<Clube> clubeopt = service.buscarClubePorId(id);
        return clubeopt.map(clube -> {
            ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(
                    clube.getNome(),
                    clube.getEstado(),
                    clube.getFk_estadio().toString(),
                    clube.getVitorias(),
                    clube.getEmpates(),
                    clube.getDerrotas(),
                    clube.getAtivo()

            );
            return ResponseEntity.ok(clubeResponseDTO);

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Page<ClubeResponseDTO>> listarClubes(Pageable pageable){
        Page<Clube> clubes = service.listarClubes(pageable);

        Page<ClubeResponseDTO> clubeDtos = clubes.map(clube -> new ClubeResponseDTO(
                clube.getNome(),
                clube.getEstado(),
                clube.getFk_estadio().toString(),
                clube.getVitorias(),
                clube.getEmpates(),
                clube.getDerrotas(),
                clube.getAtivo()

        ));

        return ResponseEntity.ok(clubeDtos);


    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubeResponseDTO> editarClube(@PathVariable Integer id,  @RequestBody ClubeRequestDto clubeResquest){


        Clube clubeExistente = service.buscarClubePorId(id)
                .orElseThrow(()-> new RuntimeException(utils.MensagensException.CLUBE_INEXISTENTE.getMensagem()));

        clubeExistente.setIdClube(id);
        clubeExistente.setNome(clubeResquest.nome());
        clubeExistente.setEstado(clubeResquest.estado());
        clubeExistente.setFk_estadio(clubeResquest.idEstadio());
        // TODO ajustar para as estatisticas tambem por favor - Nao esquecer requisito Funcional Importante

        clubeExistente.setVitorias(clubeResquest.vitorias());
        clubeExistente.setEmpates(clubeResquest.empates());
        clubeExistente.setDerrotas(clubeResquest.derrotas());


        Clube updatedCLube = service.editarClube(id, clubeExistente);

        ClubeResponseDTO responseDTO = new ClubeResponseDTO(
                updatedCLube.getNome(),
                updatedCLube.getEstado(),
                updatedCLube.getFk_estadio().toString(),
                updatedCLube.getVitorias(),
                updatedCLube.getEmpates(),
                updatedCLube.getDerrotas(),
                updatedCLube.getAtivo()


        );

        return ResponseEntity.ok(responseDTO);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarClube(@PathVariable Integer id){
        if(!clubeRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); //
        }
        service.inativarClube(id);

        return ResponseEntity.noContent().build();

    }

    // TODO -> melhorar os logs de http nas exceptions


}

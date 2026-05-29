package com.school.api.turma;

import com.school.api.turma.dto.DadosTurmaDetalhada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service){
        this.service = service;
    }

    @PostMapping("/{turmaId}/disciplinas/{disciplinaId}")
    public ResponseEntity<Void> adicionarDisciplina(
            @PathVariable Long turmaId,
            @PathVariable Long disciplinaId
    ){
        service.adicionarDisciplina(turmaId, disciplinaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/disciplinas")
    public ResponseEntity<List<String>> listarDisciplinas(@PathVariable Long id){
        return ResponseEntity.ok(service.listarDiscilinas(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosTurmaDetalhada> detalhar(@PathVariable Long id){
        return ResponseEntity.ok(service.detalhar(id));
    }
}

package com.school.api.turma;

import com.school.api.turma.dto.DadosTurmaDetalhada;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
@Tag(
        name = "Turmas",
        description = "Endpoints para gerenciamento de turmas e disciplinas associadas"
)
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service){
        this.service = service;
    }

    @PostMapping("/{turmaId}/disciplinas/{disciplinaId}")
    @Operation(
            summary = "Adicionar disciplina à turma",
            description = """
                    Associa uma disciplina a uma turma.
                    
                    Regras:
                    - A turma deve existir.
                    - A disciplina deve existir.
                    - A disciplina não pde estar previamente vinculada à turma.
                    """
    )
    @ApiResponse(responseCode = "204", description = "Disciplina vinculada com sucesso")
    @ApiResponse(responseCode = "400", description = "Disciplina já vinculada à turma")
    @ApiResponse(responseCode = "404", description = "Turma ou disciplina não encontrada")
    public ResponseEntity<Void> adicionarDisciplina(
            @PathVariable Long turmaId,
            @PathVariable Long disciplinaId
    ){
        service.adicionarDisciplina(turmaId, disciplinaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/disciplinas")
    @Operation(
            summary = "Listar disciplinas da turma",
            description = "Retorna todas as disciplinas associadas à turma informada"
    )
    @ApiResponse(responseCode = "200", description = "Disciplinas encontradas")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    public ResponseEntity<List<String>> listarDisciplinas(@PathVariable Long id){
        return ResponseEntity.ok(service.listarDiscilinas(id));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar turma",
            description = """
                    Retorna os dados completos da turma:
                    - Identificador
                    - Nome
                    - Série
                    - Disciplinas associadas
                    """
    )
    @ApiResponse(responseCode = "200", description = "Turma encontrada")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    public ResponseEntity<DadosTurmaDetalhada> detalhar(@PathVariable Long id){
        return ResponseEntity.ok(service.detalhar(id));
    }
}

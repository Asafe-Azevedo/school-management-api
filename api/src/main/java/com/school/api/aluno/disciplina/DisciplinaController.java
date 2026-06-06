package com.school.api.aluno.disciplina;


import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.aluno.disciplina.dto.DadosDetalhamentoDisciplina;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplinas")
@Tag(
        name = "Disciplinas",
        description = "Endpoints para gerenciamento das disciplinas da instituição"
)
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service){
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Cadastrar disciplina",
            description = """
                    Realiza o cadastro de uma disciplina.
                    
                    Regras:
                    - O professor informado deve existir.
                    - Cada disciplina possui um professor responsável.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Disciplina cadastrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<DadosDetalhamentoDisciplina> cadastrar(@RequestBody DadosCadastroDisciplina dados) {
        Disciplina disciplina = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoDisciplina(disciplina));
    }
}

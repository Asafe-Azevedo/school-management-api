package com.school.api.aluno.disciplina;


import com.school.api.aluno.disciplina.dto.DadosAtualizacaoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.aluno.disciplina.dto.DadosDetalhamentoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosListagemDisciplina;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DadosDetalhamentoDisciplina> cadastrar(@RequestBody @Valid DadosCadastroDisciplina dados) {
        Disciplina disciplina = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoDisciplina(disciplina));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @Operation(
            summary = "Listar disciplinas",
            description = """
                    Retorna uma lista paginada das disciplinas ativas cadastradas.
                    
                    Informações retornadas:
                    - Identificador da disciplina.
                    - Nome da disciplina.
                    - Nome do professor responsável.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornadas com sucesso")
    @GetMapping
    public ResponseEntity<Page<DadosListagemDisciplina>> listar(Pageable pageable){
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @Operation(
            summary = "Detalhar disciplina",
            description = """
                    Retorna os dados completos de uma disciplina específica.
                    
                    Informações retornadas:
                    - Identificador.
                    - Nome da disciplina.
                    - Professor responsável.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada")
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    @GetMapping("/{id}")
    public  ResponseEntity<DadosDetalhamentoDisciplina> detalhar(@PathVariable Long id){
        return ResponseEntity.ok(service.detalhar(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar professor responsável",
            description = """
                    Atualiza o professor responsável por uma disciplina.
                    
                    Regras:
                    - A disciplina deve existir.
                    - O professor informado deve existir.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Professor responsável atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Disciplina ou professor não encontrados")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoDisciplina> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoDisciplina dados){
        Disciplina disciplina = service.atualizar(id, dados);

        return ResponseEntity.ok(new DadosDetalhamentoDisciplina(disciplina));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir disciplina",
            description = """
                    Realiza a exclusão lógica de uma disciplina.
                    
                    Regras:
                    - A disciplina deve existir.
                    - A disciplina não é removida do banco de dados.
                    - O status da disciplina é alterado para inativo.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Disciplina excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

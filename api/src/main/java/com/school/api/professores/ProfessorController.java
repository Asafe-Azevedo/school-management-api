package com.school.api.professores;

import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;
import com.school.api.professores.dto.DadosDetalhamentoProfessor;
import com.school.api.professores.dto.DadosListagemProfessores;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professores")
@Tag(
        name = "Professores",
        description = "Endpoints para gerenciamento de professores da instituição"
)
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service){
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Cadastrar os professores",
            description = """
                    Realiza o cadastri de um professor na instituição.
                    
                    Funcionalidades:
                    - Valida os dados informados.
                    - Formatar CPF e telefone automaticamente.
                    - Consulta o ViaCep para preenchimento automático do endereço.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Professor cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<DadosDetalhamentoProfessor> cadastrar(@RequestBody @Valid DadosCadastroProfessores dados, UriComponentsBuilder uriComponentsBuilder){
       Professor professor = service.cadastrar(dados);
       var uri = uriComponentsBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
       return ResponseEntity.created(uri).body(new DadosDetalhamentoProfessor(professor));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @GetMapping
    @Operation(
            summary = "Listar os professores",
            description = "Retorna uma lista paginada dos professores ativos cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<Page<DadosListagemProfessores>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar professor",
            description = """
                    Atualiza os dados de um professor.
                    
                    Os campos são opcionais e apenas os informados serão alterados.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<DadosDetalhamentoProfessor> atualizar(@Valid @PathVariable Long id, @RequestBody DadosAtualizacaoProfessores dadosAtualizacaoProfessores){
        Professor professor = service.atualizar(id, dadosAtualizacaoProfessores);
        return ResponseEntity.ok(new DadosDetalhamentoProfessor(professor));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir os professores",
            description = """
                    Realiza a exclusão lógica de professores.
                    
                    O registro permanece armazenado no banco de dados,
                    porém deixa de aparecer nas consultasde professores ativos.
                    """

    )
    @ApiResponse(responseCode = "204", description = "Professor excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

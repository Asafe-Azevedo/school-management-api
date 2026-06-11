package com.school.api.aluno.nota;

import com.school.api.aluno.nota.dto.DadosBoletimDetalhado;
import com.school.api.aluno.nota.dto.DadosCadastroNotas;
import com.school.api.aluno.nota.dto.DadosDetalhamentoNotas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
@Tag(
        name = "Notas e Boletins",
        description = "Endpoints responsáveis pelo lançamento de notas e geração de boletins escolares"
)
public class NotaController {

    private final NotaService service;

    public NotaController(NotaService service){
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @Operation(
            summary = "Lançar nota",
            description = """
                    Realiza o lançamento de uma nota para um aluno em uma disciplina.
                    
                    Regras:
                    - O aluno deve existir.
                    - A disciplina deve existir.
                    - A disciplina deve estar vinculada à turma do aluno.
                    - A nota deve estar entre 0 e 10.
                    - O bimestre deve estar entre 1 e 4.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Nota lançada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada")
    @ApiResponse(responseCode = "404", description = "Aluno ou disciplina não encontrados")
    @PostMapping
    public ResponseEntity<DadosDetalhamentoNotas> lancarNota(
            @RequestBody @Valid DadosCadastroNotas dados
            ){
        Nota nota = service.lancarNota(dados.alunoId(), dados.disciplinaId(), dados.valor(), dados.tipo(), dados.bimestre());
        return ResponseEntity.ok(new DadosDetalhamentoNotas(nota));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @Operation(
            summary = "Gerar boletim do aluno",
            description = """
                    Gera o boletim completo do aluno.
                    
                    Para cada disciplina são calculados:
                    - Média por bimestre.
                    - Média final.
                    - Situação final.
                    
                    Critério atual:
                    - Média final >= 6 -> APROVADO
                    - Média final < 6 -> REPROVADO
                    """
    )
    @ApiResponse(responseCode = "200", description = "Boletim gerado com sucesso")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    @GetMapping("/boletim/{alunoId}")
    public ResponseEntity<List<DadosBoletimDetalhado>> boletim(@PathVariable Long alunoId){
        return ResponseEntity.ok(service.gerarBoletim(alunoId));
    }
}

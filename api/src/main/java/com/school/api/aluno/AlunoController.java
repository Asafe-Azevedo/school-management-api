package com.school.api.aluno;

import com.school.api.aluno.dto.DadosAtualizacaoAlunos;
import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.aluno.dto.DadosDetalhamentoAluno;
import com.school.api.aluno.dto.DadosListagemAlunos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/alunos")
@RestController
@Tag(
        name = "Alunos",
        description = "Endpoints para gerenciamento de alunos da instituição"
)
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service){
        this.service = service;
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar aluno",
            description = """
                    Realiza o cadastro de uma luno na instituição.
                    
                    Funcionalidades:
                    - Valida os dados informados.
                    - Formata automaticamente o CPF.
                    - Consulta o ViaCEP para preenchimento automático do endereço.
                    - Distribui o aluno automaticamente em uma turma compatível com sua série.
                    - Prioriza turmas menos ocupadas.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Nenhuma turma disponível para a série informada")
    public ResponseEntity<DadosDetalhamentoAluno> cadastrar(@RequestBody @Valid DadosCadastroAlunos dados, UriComponentsBuilder uriComponentsBuilder){
        Aluno aluno = service.cadastrar(dados);
        var uri = uriComponentsBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoAluno(aluno));
    }

    @Operation(
            summary = "Listar alunos",
            description = "Retorna uma lista paginada dos alunos ativos cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<DadosListagemAlunos>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return ResponseEntity.ok(service.listar(pageable));
    }

    @Operation(
            summary = "Detalhar aluno",
            description = "Retorna os dados básicos de um aluno a partir de seu identificador"
    )
    @ApiResponse(responseCode = "200", description = "Aluno encontrado")
    @ApiResponse(responseCode = "404", description = "Aluno não  encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAluno> detalhar(@PathVariable Long id){
        return ResponseEntity.ok(service.detalhar(id));
    }

    @Operation(
            summary = "Atualizar aluno",
            description = """
                    Atualiza os dados cadastrais do aluno.
                    
                    Apenas os campos informados serão alterados.
                    
                    Caso um novo CEP seja informado, os dados do endereço 
                    serão atualizados automaticamente através da integração com o ViaCEP.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoAluno> atualizar(@PathVariable Long id,
                                                            @RequestBody @Valid  DadosAtualizacaoAlunos dados){
        Aluno aluno = service.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }

    @Operation(
            summary = "Excluir aluno",
            description = """
                    Realiza a exclusão lógica do aluno.
                    
                    O registro permanece armazenado no banco de dados,
                    porém deixa de aparecer nas consultas de alunos ativos
                    """
    )
    @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

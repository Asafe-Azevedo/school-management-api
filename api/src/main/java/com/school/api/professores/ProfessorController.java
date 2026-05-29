package com.school.api.professores;

import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;
import com.school.api.professores.dto.DadosDetalhamentoProfessor;
import com.school.api.professores.dto.DadosListagemProfessores;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service){
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProfessor> cadastrar(@RequestBody @Valid DadosCadastroProfessores dados, UriComponentsBuilder uriComponentsBuilder){
       Professor professor = service.cadastrar(dados);
       var uri = uriComponentsBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
       return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoProfessor(professor));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProfessores>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoProfessor> atualizar(@Valid @PathVariable Long id, @RequestBody DadosAtualizacaoProfessores dadosAtualizacaoProfessores){
        Professor professor = service.atualizar(id, dadosAtualizacaoProfessores);
        return ResponseEntity.ok(new DadosDetalhamentoProfessor(professor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

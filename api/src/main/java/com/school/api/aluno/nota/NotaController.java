package com.school.api.aluno.nota;

import com.school.api.aluno.nota.dto.DadosBoletimDetalhado;
import com.school.api.aluno.nota.dto.DadosCadastroNotas;
import com.school.api.aluno.nota.dto.DadosDetalhamentoNotas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService service;

    public NotaController(NotaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoNotas> lancarNota(
            @RequestBody DadosCadastroNotas dados
            ){
        Nota nota = service.lancarNota(dados.alunoId(), dados.disciplinaId(), dados.valor(), dados.tipo(), dados.bimestre());
        return ResponseEntity.ok(new DadosDetalhamentoNotas(nota));
    }

    @GetMapping("/boletim/{alunoId}")
    public ResponseEntity<List<DadosBoletimDetalhado>> boletim(@PathVariable Long alunoId){
        return ResponseEntity.ok(service.gerarBoletim(alunoId));
    }
}

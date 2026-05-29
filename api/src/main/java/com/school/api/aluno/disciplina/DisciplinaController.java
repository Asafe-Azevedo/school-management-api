package com.school.api.aluno.disciplina;


import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Disciplina> cadastrar(@RequestBody DadosCadastroDisciplina dados) {
        Disciplina disciplina = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }
}

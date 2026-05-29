package com.school.api.aluno.disciplina;

import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.infra.erros.NotFoundException;
import com.school.api.professores.Professor;
import com.school.api.professores.ProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository,
                             ProfessorRepository professorRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    public Disciplina cadastrar(DadosCadastroDisciplina dados){
        Professor professor = professorRepository.findById(dados.professorId())
                .orElseThrow(() -> new NotFoundException("Professor não encontrado"));

        Disciplina disciplina = new Disciplina(dados.nome(), professor);
        return disciplinaRepository.save(disciplina);
    }
}

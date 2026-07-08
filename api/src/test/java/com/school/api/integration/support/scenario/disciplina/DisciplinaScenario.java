package com.school.api.integration.support.scenario.disciplina;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaService;
import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.integration.support.factory.aluno.disciplina.DisciplinaDtoFactory;
import com.school.api.integration.support.scenario.professor.ProfessorScenario;
import com.school.api.professores.Professor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DisciplinaScenario {

    private final DisciplinaService disciplinaService;
    private final ProfessorScenario professorScenario;

    public DisciplinaScenario(DisciplinaService disciplinaService, ProfessorScenario professorScenario){
        this.disciplinaService = disciplinaService;
        this.professorScenario = professorScenario;
    }

    public Disciplina criarDisciplina(){
        Professor professor = professorScenario.criarProfessor();
        DadosCadastroDisciplina dto = DisciplinaDtoFactory.criarDisciplina(professor.getId());

        return disciplinaService.cadastrar(dto);
    }
}

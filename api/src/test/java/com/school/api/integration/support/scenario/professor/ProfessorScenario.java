package com.school.api.integration.support.scenario.professor;

import com.school.api.integration.support.factory.professor.ProfessorDtoFactory;
import com.school.api.professores.Professor;
import com.school.api.professores.ProfessorService;
import com.school.api.professores.dto.DadosCadastroProfessores;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProfessorScenario {

    private final ProfessorService professorService;

    public ProfessorScenario(ProfessorService professorService){
        this.professorService = professorService;
    }

    public Professor criarProfessor(){
        DadosCadastroProfessores dto = ProfessorDtoFactory.criarProfessor();
        return professorService.cadastrar(dto);
    }
}

package com.school.api.integration.controller.nota;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.nota.dto.DadosCadastroNotas;
import com.school.api.integration.support.controller.nota.NotaControllerHelper;
import com.school.api.integration.support.scenario.aluno.AlunoScenario;
import com.school.api.integration.support.scenario.disciplina.DisciplinaScenario;
import com.school.api.integration.support.scenario.turma.TurmaScenario;
import com.school.api.turma.Turma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.school.api.integration.support.factory.aluno.nota.NotaDtoFactory.criarNota;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("NotaController")
class NotaControllerIT extends NotaControllerHelper {

    @Autowired
    private TurmaScenario turmaScenario;

    @Autowired
    private DisciplinaScenario disciplinaScenario;

    @Autowired
    private AlunoScenario alunoScenario;

    private void vincularDisciplinaNaTurma(Turma turma, Disciplina disciplina) throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/turmas/" + turma.getId() + "/disciplinas/" + disciplina.getId()))
                .andExpect(status().isNoContent());
    }

    @Nested
    @DisplayName("POST / notas - Lançamento")
    class LancarNota{

        @Test
        @DisplayName("Deve lançar nota com sucesso")
        @WithMockUser(roles = "ADMIN")
        void deveLancarNota() throws Exception{

            Turma turma = turmaScenario.criarTurmaDisponivel();
            Disciplina disciplina = disciplinaScenario.criarDisciplina();
            vincularDisciplinaNaTurma(turma, disciplina);
            Aluno aluno = alunoScenario.criarAluno(turma);
            DadosCadastroNotas dto = criarNota(aluno.getId(), disciplina.getId());

            mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.valor").value(8.5))
                    .andExpect(jsonPath("$.tipo").value("PROVA"))
                    .andExpect(jsonPath("$.bimestre").value(1));
        }

    }

    @Nested
    @DisplayName("GET /notas/boletim/{alunoId}")
    class GerarBoletim{

        @Test
        @DisplayName("Deve gerar boletim")
        @WithMockUser(roles = "ADMIN")
        void deveGerarBoletim() throws Exception{

            Turma turma = turmaScenario.criarTurmaDisponivel();
            Disciplina disciplina = disciplinaScenario.criarDisciplina();
            vincularDisciplinaNaTurma(turma, disciplina);
            Aluno aluno = alunoScenario.criarAluno(turma);
            DadosCadastroNotas dto = criarNota(aluno.getId(), disciplina.getId());

            mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(dto)))
                    .andExpect(status().isOk());

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/boletim/" + aluno.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].mediaFinal").value(8.5))
                    .andExpect(jsonPath("$[0].situacao").value("APROVADO"));
        }
    }
}

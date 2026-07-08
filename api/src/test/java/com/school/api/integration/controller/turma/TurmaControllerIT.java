package com.school.api.integration.controller.turma;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.integration.support.controller.turma.TurmaControllerHelper;
import com.school.api.integration.support.scenario.disciplina.DisciplinaScenario;
import com.school.api.integration.support.scenario.turma.TurmaScenario;
import com.school.api.turma.Turma;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TurmaController")
class TurmaControllerIT extends TurmaControllerHelper {

    @Autowired
    private TurmaScenario turmaScenario;

    @Autowired
    private DisciplinaScenario disciplinaScenario;

    @Nested
    @DisplayName("POST /disciplinas - Adicionar disciplina")
    class AdicionarDisciplina{

        @Test
        @DisplayName("Deve adicionar disciplina")
        @WithMockUser(roles = "ADMIN")
        void deveAdicionarDisciplina() throws Exception{
            Turma turma = turmaScenario.criarTurmaDisponivel();
            Disciplina disciplina = disciplinaScenario.criarDisciplina();

            mockMvc.perform(
                    MockMvcRequestBuilders.post(BASE_URL + "/" + turma.getId() + "/disciplinas/" + disciplina.getId()))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("GET /turmas/{id}/disciplinas - Listar disciplina")
    class ListarDisciplina{

        @Test
        @DisplayName("Deve listar disciplinas")
        @WithMockUser(roles = "ADMIN")
        void deveListarDisciplinas() throws Exception{
            Turma turma = turmaScenario.criarTurmaDisponivel();
            Disciplina disciplina = disciplinaScenario.criarDisciplina();

            mockMvc.perform(
                    MockMvcRequestBuilders.post(BASE_URL + "/" + turma.getId() + "/disciplinas/" + disciplina.getId()))
                    .andExpect(status().isNoContent());

            mockMvc.perform(
                    MockMvcRequestBuilders.get(BASE_URL + "/" + turma.getId() + "/disciplinas"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").value(disciplina.getNome()));
        }
    }

    @Nested
    @DisplayName("GET /turmas/{id} - Detalhar turma")
    class DetalharTurma{

        @Test
        @DisplayName("Deve detalhar turma")
        @WithMockUser(roles = "ADMIN")
        void deveDetalharTurma()  throws Exception{
            Turma turma = turmaScenario.criarTurmaDisponivel();

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + turma.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(turma.getId()))
                    .andExpect(jsonPath("$.nome").value(turma.getNome()));
        }
    }
}

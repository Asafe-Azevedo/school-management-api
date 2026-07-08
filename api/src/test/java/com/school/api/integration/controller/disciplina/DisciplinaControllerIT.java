package com.school.api.integration.controller.disciplina;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.dto.DadosAtualizacaoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.integration.support.controller.disciplina.DisciplinaControllerHelper;
import com.school.api.integration.support.scenario.disciplina.DisciplinaScenario;
import com.school.api.integration.support.scenario.professor.ProfessorScenario;
import com.school.api.professores.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.school.api.integration.support.factory.aluno.disciplina.DisciplinaDtoFactory.criarDisciplina;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("DisciplinaController")
class DisciplinaControllerIT extends DisciplinaControllerHelper {

    @Autowired
    private DisciplinaScenario disciplinaScenario;

    @Autowired
    private ProfessorScenario professorScenario;

    @Nested
    @DisplayName("POST /disciplinas - Cadastro")
    class CadastrarDisciplina{

        @Test
        @DisplayName("Deve cadastrar disciplina com sucesso")
        @WithMockUser(roles = "ADMIN")
        void deveCadastrarDisciplinaComSucesso() throws Exception{

            Professor professor = professorScenario.criarProfessor();
            DadosCadastroDisciplina dto = criarDisciplina(professor.getId());

            mockMvc.perform(
                            MockMvcRequestBuilders.post(BASE_URL)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(toJson(dto))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.nome")
                            .value(dto.nome()))
                    .andExpect(jsonPath("$.professor")
                            .exists());
        }
    }

    @Nested
    @DisplayName("GET /disciplinas - Listagem")
    class ListarDisciplina{

        @Test
        @DisplayName("Deve listar disciplinas")
        @WithMockUser(roles = "ADMIN")
        void deveListarDisciplinas() throws Exception{
            disciplinaScenario.criarDisciplina();

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray());
        }
    }

    @Nested
    @DisplayName("GET /disciplinas/{id} - Detalhar")
    class DetalharDisciplina{

        @Test
        @DisplayName("Deve retornar disciplina por ID")
        @WithMockUser(roles = "ADMIN")
        void deveRetornarDisciplinaPorID() throws Exception{

            Disciplina disciplina = disciplinaScenario.criarDisciplina();

            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + disciplina.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(disciplina.getId()))
                    .andExpect(jsonPath("$.nome").value(disciplina.getNome()));

        }
    }

    @Nested
    @DisplayName("PUT /disciplinas/{id} - Atualizar")
    class AtualizarDisciplina{

        @Test
        @DisplayName("Deve atualizar professor da disciplina")
        @WithMockUser(roles = "ADMIN")
        void deveAtualizarProfessorDaDisciplina() throws Exception{
            Disciplina disciplina = disciplinaScenario.criarDisciplina();
            Professor novoProfessor = professorScenario.criarProfessor();

            DadosAtualizacaoDisciplina update = new DadosAtualizacaoDisciplina(novoProfessor.getId());

            mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + disciplina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(update)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.professor")
                            .exists());
        }
    }

    @Nested
    @DisplayName("DELETE /disciplinas/{id} - Excluir")
    class ExcluirDisciplina{

        @Test
        @DisplayName("Deve excluir disciplina")
        @WithMockUser(roles = "ADMIN")
        void deveExcluirDisciplina() throws Exception{
            Disciplina disciplina = disciplinaScenario.criarDisciplina();

            mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + disciplina.getId()))
                    .andExpect(status().isNoContent());
        }
    }
}

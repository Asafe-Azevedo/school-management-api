package com.school.api.integration.controller.professor;

import com.school.api.integration.support.controller.professor.ProfessorControllerHelper;
import com.school.api.integration.support.scenario.professor.ProfessorScenario;
import com.school.api.professores.Professor;
import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.school.api.integration.support.factory.professor.ProfessorDtoFactory.criarProfessor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("ProfessorController")
class ProfessorControllerIT extends ProfessorControllerHelper {

    @Autowired
    private ProfessorScenario professorScenario;

    @Nested
    @DisplayName("POST /professores - Cadastro de professor")
    class CadastrarProfessor{

        @Test
        @DisplayName("Deve cadastrar professor com sucesso e retornar 201")
        @WithMockUser(roles = "ADMIN")
        void deveCadastrarProfessorComSucesso() throws Exception{

            DadosCadastroProfessores dto = criarProfessor();
            String json = toJson(dto);

            var response = mockMvc.perform(
                    MockMvcRequestBuilders.post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json));

            response.andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.nome").value(dto.nome()))
                    .andExpect(jsonPath("$.email").value(dto.email()));
        }
    }

    @Nested
    @DisplayName("GET /professores - Listar professor")
    class ListarProfessor {

        @Test
        @DisplayName("Deve listar professores")
        @WithMockUser(roles = "ADMIN")
        void deveListarProfessores() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray());
        }

        @Test
        @DisplayName("Deve retornar professor quando ID existir")
        @WithMockUser(roles = "ADMIN")
        void deveRetornarProfessorPorID() throws Exception {

            Professor professor = professorScenario.criarProfessor();

            mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_URL + "/" + professor.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(professor.getId()))
                    .andExpect(jsonPath("$.nome").value(professor.getNome()));
        }
    }

    @Nested
    @DisplayName("PUT /professores - Atualizar professor")
    class AtualizarProfessor{

        @Test
        @DisplayName("Deve atualizar professor")
        @WithMockUser(roles = "ADMIN")
        void deveAtualizarProfessor() throws Exception{

            Professor professor = professorScenario.criarProfessor();

            DadosAtualizacaoProfessores update = new DadosAtualizacaoProfessores(
                    "Nome Atualizado",
                    null,
                    null
            );

            String jsonUpdate = toJson(update);

            mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + professor.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdate))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
        }
    }

    @Nested
    @DisplayName("DELETE /professores - Excluir professor")
    class ExcluirProfessor {

        @Test
        @DisplayName("Deve excluir professor")
        @WithMockUser(roles = "ADMIN")
        void deveExcluirProfessor() throws Exception {

            Professor professor = professorScenario.criarProfessor();

            mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + professor.getId()))
                    .andExpect(status().isNoContent());
        }
    }
}

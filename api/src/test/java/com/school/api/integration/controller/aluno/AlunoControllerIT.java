package com.school.api.integration.controller.aluno;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.dto.DadosAtualizacaoAlunos;
import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.integration.support.controller.aluno.AlunoControllerHelper;
import com.school.api.integration.support.scenario.aluno.AlunoScenario;
import com.school.api.integration.support.scenario.turma.TurmaScenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.school.api.integration.support.factory.aluno.AlunoDtoFactory.criarAluno;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("AlunoController")
class AlunoControllerIT extends AlunoControllerHelper {

    @Autowired
    protected AlunoScenario alunoScenario;

    @Autowired
    protected TurmaScenario turmaScenario;


    @Nested
    @DisplayName("POST /alunos - Cadastro de aluno")
    class CadastrarAluno{

        @Test
        @DisplayName("Deve cadastrar aluno com sucesso e retornar 201")
        @WithMockUser(roles = "ADMIN")
        void deveCadastrarAlunoComSucesso() throws Exception {

            turmaScenario.criarTurmaDisponivel();
            DadosCadastroAlunos dto = criarAluno();
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
    @DisplayName("GET /alunos - Listar aluno")
    class ListarAluno {

        @Test
        @DisplayName("Deve listar alunos")
        @WithMockUser(roles = "ADMIN")
        void deveListarAlunos() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray());
        }

        @Test
        @DisplayName("Deve retornar aluno quando ID existir")
        @WithMockUser(roles = "ADMIN")
        void deveRetornarAlunoPorID() throws Exception {

            Aluno aluno = alunoScenario.criarAluno();

            mockMvc.perform(
                    MockMvcRequestBuilders.get(BASE_URL + "/" + aluno.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(aluno.getId()))
                    .andExpect(jsonPath("$.nome").value(aluno.getNome()));
        }
    }

    @Nested
    @DisplayName("PUT /alunos - Atualizar aluno")
    class AtualizarAluno{

        @Test
        @DisplayName("Deve atualizar aluno")
        @WithMockUser(roles = "ADMIN")
        void deveAtualizarAluno() throws Exception{

           Aluno aluno = alunoScenario.criarAluno();

           DadosAtualizacaoAlunos update = new DadosAtualizacaoAlunos(
                "Nome Atualizado",
                null,
                null,
                null
           );

           String jsonUpdate = toJson(update);

           mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + aluno.getId())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(jsonUpdate))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
        }
    }

    @Nested
    @DisplayName("DELETE /alunos - Excluir aluno")
    class ExcluirAluno{

        @Test
        @DisplayName("Deve excluir aluno")
        @WithMockUser(roles = "ADMIN")
        void deveExcluirAluno() throws Exception {

           Aluno aluno = alunoScenario.criarAluno();

            mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + aluno.getId()))
                    .andExpect(status().isNoContent());
        }
    }
}

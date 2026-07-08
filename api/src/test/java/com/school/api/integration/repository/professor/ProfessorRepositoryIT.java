package com.school.api.integration.repository.professor;

import com.school.api.integration.support.factory.EnderecoFactory;
import com.school.api.integration.support.factory.professor.ProfessorFactory;
import com.school.api.professores.ProfessorRepository;
import com.school.api.integration.support.integration.BaseRepositoryIT;
import com.school.api.integration.support.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProfessorRepository")
class ProfessorRepositoryIT extends BaseRepositoryIT {

    @Autowired
    private ProfessorRepository professorRepository;

    @Nested
    @DisplayName("existsByCpf")
    class ExistsByCpf{
        @Test
        @DisplayName("Deve retornar true quando CPF existir")
        void deveRetornarTrueQuandoCpfExistir(){
            var professor = ProfessorFactory.criarProfessor();
            professorRepository.save(professor);

            assertTrue(professorRepository.existsByCpf(professor.getCpf()));
        }

        @Test
        @DisplayName("Deve retornar false quando CPF não exisitr")
        void deveRetornarFalseQuandoCpfNaoExistir(){
            assertFalse(professorRepository.existsByCpf(TestConstants.CPF_INEXISTENTE));
        }
    }

    @Nested
    @DisplayName("findAllByAtivoTrue")
    class FindAllByAtivoTrue{
        @Test
        @DisplayName("Deve listar somente professores ativos")
        void deveListarSomenteProfessoresAtivos(){
            professorRepository.save(ProfessorFactory.criarProfessor());

            professorRepository.save(ProfessorFactory.criarProfessorInativo());

            var pagina = professorRepository.findAllByAtivoTrue(PageRequest.of(0, 10));

            assertAll(
                    () -> assertEquals(1, pagina.getTotalElements()),
                    () -> assertFalse(pagina.isEmpty()),
                    () -> assertTrue(pagina.getContent().get(0).getAtivo())
            );
        }

        @Test
        @DisplayName("Deve retornar página vazia quando não existirem professores ativos")
        void deveRetornarPaginaVaziaQuandoNaoExistiremProfessoresAtivos(){
            professorRepository.save(ProfessorFactory.criarProfessorInativo());
            var pagina = professorRepository.findAllByAtivoTrue(PageRequest.of(0,10));

            assertAll(
                    () -> assertTrue(pagina.isEmpty()),
                    () -> assertEquals(0, pagina.getTotalElements()),
                    () -> assertEquals(0, pagina.getContent().size())
            );
        }

        @Test
        @DisplayName("Deve respeitar paginação")
        void deveRespeitarPaginacao(){
            for(int i = 0; i < 15; i++){
                professorRepository.save(ProfessorFactory.criarProfessor());
            }
            var pagina = professorRepository.findAllByAtivoTrue(PageRequest.of(0,10));

            assertAll(
                    () -> assertEquals(10, pagina.getContent().size()),
                    () -> assertEquals(15, pagina.getTotalElements()),
                    () -> assertEquals(2, pagina.getTotalPages())
            );
        }
    }

    @Nested
    @DisplayName("Persistência")
    class PersistenciaIT{
        @Test
        @DisplayName("Deve salvar professor")
        void deveSalvarProfessor(){
            var esperado = ProfessorFactory.criarProfessor();

            var salvo = professorRepository.save(esperado);

            assertAll(
                    () -> assertNotNull(salvo.getId()),
                    () -> assertEquals(esperado.getNome(), salvo.getNome()),
                    () -> assertEquals(esperado.getEmail(), salvo.getEmail()),
                    () -> assertEquals(esperado.getCpf(), salvo.getCpf()),
                    () -> assertTrue(salvo.getAtivo())
            );
        }

        @Test
        @DisplayName("Deve atualizar o nome do professor")
        void deveAtualizarNome(){
            var professor = ProfessorFactory.criarProfessor();

            String emailOriginal = professor.getEmail();
            String cpfOriginal = professor.getCpf();

            professor = professorRepository.save(professor);

            professor.atualizarNome("Novo Nome");

            professorRepository.saveAndFlush(professor);

            var alunoAtualizado = professorRepository.findById(professor.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals("Novo Nome", alunoAtualizado.getNome()),
                    () -> assertEquals(emailOriginal, alunoAtualizado.getEmail()),
                    () -> assertEquals(cpfOriginal, alunoAtualizado.getCpf())
            );
        }

        @Test
        @DisplayName("Deve atualizar o telefone do professor")
        void deveAtualizarTelefoneProfessor() {

            var professor = ProfessorFactory.criarProfessor();

            String nomeOriginal = professor.getNome();
            String emailOriginal = professor.getEmail();

            professor = professorRepository.save(professor);

            professor.atualizarTelefone("11999999999");

            professorRepository.saveAndFlush(professor);

            var atualizado = professorRepository.findById(professor.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals("11999999999", atualizado.getTelefone()),
                    () -> assertEquals(nomeOriginal, atualizado.getNome()),
                    () -> assertEquals(emailOriginal, atualizado.getEmail())
            );
        }

        @Test
        @DisplayName("Deve atualizar o endereço")
        void deveAtualizarEndereco() {

            var professor = professorRepository.save(ProfessorFactory.criarProfessor());

            var novoEndereco = EnderecoFactory.criarEnderecoAtualizado();

            professor.atualizarEndereco(novoEndereco);

            professorRepository.saveAndFlush(professor);

            var atualizado = professorRepository.findById(professor.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals(novoEndereco.getCep(), atualizado.getEndereco().getCep()),
                    () -> assertEquals(novoEndereco.getRua(), atualizado.getEndereco().getRua()),
                    () -> assertEquals(novoEndereco.getNumero(), atualizado.getEndereco().getNumero()),
                    () -> assertEquals(novoEndereco.getBairro(), atualizado.getEndereco().getBairro()),
                    () -> assertEquals(novoEndereco.getCidade(), atualizado.getEndereco().getCidade()),
                    () -> assertEquals(novoEndereco.getUf(), atualizado.getEndereco().getUf()),
                    () -> assertEquals(novoEndereco.getComplemento(), atualizado.getEndereco().getComplemento())
            );
        }

        @Test
        @DisplayName("Deve realizar exclusão lógica")
        void deveRealizarExclusaoLogica() {

            var professor = professorRepository.save(ProfessorFactory.criarProfessor());

            professor.excluir();

            professorRepository.saveAndFlush(professor);

            var atualizado = professorRepository.findById(professor.getId()).orElseThrow();

            assertFalse(atualizado.getAtivo());
        }
    }

}

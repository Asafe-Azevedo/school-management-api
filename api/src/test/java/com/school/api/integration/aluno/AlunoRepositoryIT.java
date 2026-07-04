package com.school.api.integration.aluno;

import com.school.api.aluno.AlunoRepository;
import com.school.api.support.factory.EnderecoFactory;
import com.school.api.support.factory.aluno.AlunoFactory;
import com.school.api.support.integration.BaseRepositoryIT;
import com.school.api.support.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AlunoRepository")
class AlunoRepositoryIT extends BaseRepositoryIT {

    @Autowired
    private AlunoRepository alunoRepository;

    @Nested
    @DisplayName("existsByCpf")
    class ExistsByCpf{
        @Test
        @DisplayName("Deve retornar true quando CPF existir")
        void deveRetornarTrueQuandoCpfExistir(){
            var aluno = AlunoFactory.criarAluno();
            alunoRepository.save(aluno);

            assertTrue(alunoRepository.existsByCpf(aluno.getCpf()));
        }

        @Test
        @DisplayName("Deve retornar false quando CPF não existir")
        void deveRetornarFalseQuandoCpfNaoExistir(){
            assertFalse(alunoRepository.existsByCpf(TestConstants.CPF_INEXISTENTE));
        }
    }

    @Nested
    @DisplayName("findAllByAtivoTrue")
    class FindAllByAtivoTrue{
        @Test
        @DisplayName("Deve listar somente alunos ativos")
        void deveListarSomenteAlunosAtivos(){
            alunoRepository.save(AlunoFactory.criarAluno());
            alunoRepository.save(AlunoFactory.criarAlunoInativo());

            var pagina = alunoRepository.findAllByAtivoTrue(PageRequest.of(0,10));

            assertAll(
                    () -> assertEquals(1, pagina.getTotalElements()),
                    () -> assertFalse(pagina.isEmpty()),
                    () -> assertTrue(pagina.getContent().get(0).getAtivo())
            );
        }

        @Test
        @DisplayName("Deve retornar página vazia quando não existirem alunos ativos")
        void deveRetornarPaginaVaziaQuandoNaoExistiremAlunosAtivos(){
            alunoRepository.save(AlunoFactory.criarAlunoInativo());
            var pagina = alunoRepository.findAllByAtivoTrue(PageRequest.of(0,10));

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
                alunoRepository.save(AlunoFactory.criarAluno());
            }
            var pagina = alunoRepository.findAllByAtivoTrue(PageRequest.of(0,10));

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
        @DisplayName("Deve salvar aluno")
        void deveSalvarAluno(){
            var esperado = AlunoFactory.criarAluno();

            var salvo = alunoRepository.save(esperado);

            assertAll(
                    () -> assertNotNull(salvo.getId()),
                    () -> assertEquals(esperado.getNome(), salvo.getNome()),
                    () -> assertEquals(esperado.getEmail(), salvo.getEmail()),
                    () -> assertEquals(esperado.getCpf(), salvo.getCpf()),
                    () -> assertTrue(salvo.getAtivo())
            );
        }

        @Test
        @DisplayName("Deve atualizar o nome do aluno")
        void deveAtualizarNome(){
            var aluno = AlunoFactory.criarAluno();

            String emailOriginal = aluno.getEmail();
            String cpfOriginal = aluno.getCpf();

            aluno = alunoRepository.save(aluno);

            aluno.atualizarNome("Novo Nome");

            alunoRepository.saveAndFlush(aluno);

            var alunoAtualizado = alunoRepository.findById(aluno.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals("Novo Nome", alunoAtualizado.getNome()),
                    () -> assertEquals(emailOriginal, alunoAtualizado.getEmail()),
                    () -> assertEquals(cpfOriginal, alunoAtualizado.getCpf())
            );
        }

        @Test
        @DisplayName("Deve atualizar o e-mail do aluno")
        void deveAtualizarEmail() {

            var aluno = AlunoFactory.criarAluno();

            String nomeOriginal = aluno.getNome();
            String cpfOriginal = aluno.getCpf();

            aluno = alunoRepository.save(aluno);

            aluno.atualizarEmail("novo@email.com");

            alunoRepository.saveAndFlush(aluno);

            var atualizado = alunoRepository.findById(aluno.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals(nomeOriginal, atualizado.getNome()),
                    () -> assertEquals("novo@email.com", atualizado.getEmail()),
                    () -> assertEquals(cpfOriginal, atualizado.getCpf())
            );
        }

        @Test
        @DisplayName("Deve atualizar o telefone do responsável")
        void deveAtualizarTelefoneResponsavel() {

            var aluno = AlunoFactory.criarAluno();

            String nomeOriginal = aluno.getNome();
            String emailOriginal = aluno.getEmail();

            aluno = alunoRepository.save(aluno);

            aluno.atualizarTelefoneResponsavel("11999999999");

            alunoRepository.saveAndFlush(aluno);

            var atualizado = alunoRepository.findById(aluno.getId()).orElseThrow();

            assertAll(
                    () -> assertEquals("11999999999", atualizado.getTelefoneResponsavel()),
                    () -> assertEquals(nomeOriginal, atualizado.getNome()),
                    () -> assertEquals(emailOriginal, atualizado.getEmail())
            );
        }

        @Test
        @DisplayName("Deve atualizar o endereço")
        void deveAtualizarEndereco() {

            var aluno = alunoRepository.save(AlunoFactory.criarAluno());

            var novoEndereco = EnderecoFactory.criarEnderecoAtualizado();

            aluno.atualizarEndereco(novoEndereco);

            alunoRepository.saveAndFlush(aluno);

            var atualizado = alunoRepository.findById(aluno.getId()).orElseThrow();

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

            var aluno = alunoRepository.save(AlunoFactory.criarAluno());

            aluno.excluir();

            alunoRepository.saveAndFlush(aluno);

            var atualizado = alunoRepository.findById(aluno.getId()).orElseThrow();

            assertFalse(atualizado.getAtivo());
        }
    }
}

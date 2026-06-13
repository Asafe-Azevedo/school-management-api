package com.school.api.aluno.disciplina;

import com.school.api.aluno.disciplina.dto.DadosAtualizacaoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.aluno.disciplina.dto.DadosDetalhamentoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosListagemDisciplina;
import com.school.api.infra.erros.disciplina.DisciplinaNaoEncontradaException;
import com.school.api.infra.erros.professores.ProfessorNaoEncontradoException;
import com.school.api.professores.Professor;
import com.school.api.professores.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository,
                             ProfessorRepository professorRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    @Transactional
    public Disciplina cadastrar(DadosCadastroDisciplina dados){
        Professor professor = professorRepository.findById(dados.professorId())
                .orElseThrow(ProfessorNaoEncontradoException::new);

        Disciplina disciplina = new Disciplina(dados.nome(), professor);
        return disciplinaRepository.save(disciplina);
    }

    public Page<DadosListagemDisciplina> listar(Pageable pageable){

        return disciplinaRepository.findAllByAtivoTrue(pageable).map(DadosListagemDisciplina::new);
    }

    public DadosDetalhamentoDisciplina detalhar(Long id){
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(DisciplinaNaoEncontradaException::new);

        return new DadosDetalhamentoDisciplina(disciplina);
    }

    @Transactional
    public Disciplina atualizar(Long id, DadosAtualizacaoDisciplina dados){
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(DisciplinaNaoEncontradaException::new);
        Professor professor = professorRepository.findById(dados.professorId()).orElseThrow(ProfessorNaoEncontradoException::new);

        disciplina.atualizarProfessor(professor);

        return disciplina;
    }

    @Transactional
    public void excluir(Long id){
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(DisciplinaNaoEncontradaException::new);

        disciplina.excluir();
    }
}

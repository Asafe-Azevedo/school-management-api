package com.school.api.turma;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.infra.erros.disciplina.DisciplinaNaoEncontradaException;
import com.school.api.infra.erros.turmas.TurmaNaoEncontradaException;
import com.school.api.turma.dto.DadosTurmaDetalhada;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public TurmaService(TurmaRepository turmaRepository, DisciplinaRepository disciplinaRepository) {
        this.turmaRepository = turmaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Transactional
    public void adicionarDisciplina(Long turmaId, Long disciplinaId){

        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(TurmaNaoEncontradaException::new);

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(DisciplinaNaoEncontradaException::new);

        if(turma.getDisciplinas().contains(disciplina)){
            throw new RegraNegocioException("Disciplina já vinculada à turma");
        }

        turma.getDisciplinas().add(disciplina);

        turmaRepository.save(turma);
    }

    public List<String> listarDisciplinas(Long turmaId){
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(TurmaNaoEncontradaException::new);

        return turma.getDisciplinas()
                .stream()
                .map(Disciplina::getNome)
                .toList();
    }

    public DadosTurmaDetalhada detalhar(Long turmaId){
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(TurmaNaoEncontradaException::new);

        List<String> disciplinas = turma.getDisciplinas()
                .stream()
                .map(Disciplina::getNome)
                .toList();

        return new DadosTurmaDetalhada(
                turma.getId(),
                turma.getNome(),
                turma.getSerie(),
                disciplinas
        );
    }
}

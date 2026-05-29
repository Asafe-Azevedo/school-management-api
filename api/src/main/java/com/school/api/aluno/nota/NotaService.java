package com.school.api.aluno.nota;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.AlunoRespository;
import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.aluno.nota.dto.DadosBoletim;
import com.school.api.aluno.nota.dto.DadosBoletimDetalhado;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.infra.erros.alunos.AlunoNaoEncontradoException;
import com.school.api.infra.erros.disciplina.DisciplinaNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRespository alunoRespository;
    private final DisciplinaRepository disciplinaRepository;

    public NotaService(NotaRepository notaRepository,
                       AlunoRespository alunoRespository,
                       DisciplinaRepository disciplinaRepository){
        this.notaRepository = notaRepository;
        this.alunoRespository = alunoRespository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public Nota lancarNota(Long alunoId, Long disciplinaId, Double valor, TipoNota tipo, Integer bimestre){
        Aluno aluno = alunoRespository.findById(alunoId)
                .orElseThrow(AlunoNaoEncontradoException::new);

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(DisciplinaNaoEncontradaException::new);

        if(!aluno.getTurma().getDisciplinas().contains(disciplina)){
            throw new IllegalArgumentException("Aluno não está matriculado nessa disciplina");
        }

        if(valor < 0 || valor > 10){
            throw new RegraNegocioException("Nota deve ser entre 0 e 10");
        }

        if(bimestre < 1 || bimestre > 4){
            throw new IllegalArgumentException("Bimestre deve ser entre 1 e 4");
        }

        Nota nota = new Nota(null, valor, tipo, bimestre, aluno, disciplina);

        return notaRepository.save(nota);
    }

    public List<DadosBoletimDetalhado> gerarBoletim(Long alunoId){
        List<Nota> notas = notaRepository.findByAlunoId(alunoId);

        Map<Disciplina,List<Nota>> porDisciplina = notas.stream()
                .collect(Collectors.groupingBy(Nota::getDisciplina));

        List<DadosBoletimDetalhado> boletim = new ArrayList<>();

        for(var entry : porDisciplina.entrySet()){
            Disciplina disciplina = entry.getKey();
            List<Nota> notasDisciplinas = entry.getValue();

            Map<Integer, Double> mediasPorBimestre = notasDisciplinas.stream()
                    .collect(Collectors.groupingBy(Nota::getBimestre, Collectors.averagingDouble(Nota::getValor)));

            double mediaFinal = mediasPorBimestre.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            String situacao = mediaFinal >= 6 ? "APROVADO" : "REPROVADO";

            boletim.add(new DadosBoletimDetalhado(disciplina.getNome(), mediasPorBimestre, mediaFinal, situacao));
        }

        return boletim;
    }
}

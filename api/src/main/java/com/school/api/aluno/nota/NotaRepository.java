package com.school.api.aluno.nota;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByAlunoId(Long alunoid);

    List<Nota> findByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);
}

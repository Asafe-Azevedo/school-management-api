package com.school.api.aluno.disciplina;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Page<Disciplina> findAllByAtivoTrue(Pageable pageable);
}

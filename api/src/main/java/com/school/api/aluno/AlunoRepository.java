package com.school.api.aluno;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByCpf(String cpf);

    Page<Aluno> findAllByAtivoTrue(Pageable pageable);

}

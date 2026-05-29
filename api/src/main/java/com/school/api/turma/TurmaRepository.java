package com.school.api.turma;

import com.school.api.turma.Serie;
import com.school.api.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("""
            SELECT t FROM Turma t
            LEFT JOIN t.alunos a
            WHERE t.serie = :serie
            GROUP BY t
            ORDER BY COUNT(a) ASC
            """)
    List<Turma> buscarTurmasOrdenadasPorSerieOrdenadas(Serie serie);
}

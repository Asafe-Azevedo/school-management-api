CREATE TABLE turma_disciplinas (

turma_id BIGINT,
disciplina_id BIGINT,

PRIMARY KEY (turma_id, disciplina_id),

FOREIGN KEY (turma_id) REFERENCES turmas(id),
FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id)
);
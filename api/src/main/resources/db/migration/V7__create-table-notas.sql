CREATE TABLE notas (

id BIGINT AUTO_INCREMENT PRIMARY KEY,

aluno_id BIGINT NOT NULL,
disciplina_id BIGINT NOT NULL,

valor DECIMAL(5,2) NOT NULL,
bimestre INT NOT NULL,

CONSTRAINT fk_nota_aluno
FOREIGN KEY (aluno_id)
REFERENCES alunos(id),

CONSTRAINT fk_nota_disciplina
FOREIGN KEY (disciplina_id)
REFERENCES disciplinas(id)
);
CREATE TABLE disciplinas (

id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
professor_id BIGINT,

CONSTRAINT fk_disciplina_professor
FOREIGN KEY (professor_id)
REFERENCES professores(id)
);
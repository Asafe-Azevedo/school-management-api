CREATE TABLE alunos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,

    data_nascimento DATE NOT NULL,
    nome_responsavel VARCHAR(150) NOT NULL,
    telefone_responsavel VARCHAR(20) NOT NULL,

    serie VARCHAR(50) NOT NULL,

    ativo BOOLEAN NOT NULL,

    -- Endereço (embedded)
    cep VARCHAR(9),
    rua VARCHAR(150),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    complemento VARCHAR(150),

    turma_id BIGINT,

    CONSTRAINT fk_aluno_turma
        FOREIGN KEY (turma_id)
        REFERENCES turmas(id)
);

UPDATE turmas SET capacidade = 45;
create table professores(
    id bigint not null auto_increment,
    nome varchar(150) not null,
    email varchar(100) not null unique,
    telefone varchar(15) not null unique,
    cpf varchar(15) not null unique,
    especialidade varchar(120) not null,
    cep varchar(10) not null,
    rua varchar(200) not null,
    numero varchar(20) not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    uf varchar(2) not null,
    complemento varchar(100),

    primary key(id)
)
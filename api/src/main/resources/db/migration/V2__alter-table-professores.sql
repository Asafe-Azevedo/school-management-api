alter table professores
add ativo TINYINT(1) not null default 1;
update professores set ativo = 1;
School Management API - API de Gerenciamento Escolar

REST API for school management developed with Spring Boot.
The system allows management of students, teachers, classes, subjects and grades, including generation of report cards and automatic distribution of students into classes.
----------------------------------------------------------------------------------------------------------------------
API REST para gerenciamento escolar desenvolvida com Spring Boot.
O sistema permite gerenciamento de alunos, professores, turmas, disciplinas e notas, incluindo geração de boletins e distribuiçao automática de alunos em turmas.

Tecnologias Utilizadas: 
- Java 17
- Spring Boot 4.0.5
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- WebCLient
- ViaCep API
- Flyway Migration

Funcionalidades:
Gerenciamento de Alunos 
- Cadastro completo de alunos
- Atualização de dados cadastrais
- Exclusão lógica de alunos
- Listagem paginada
- Busca detalhada por ID
- Associação automática de alunos em turmas
- Controle de série escolar
- Validação e formatação de CPF

Gerenciamento de Professores
- Cadastro de professores
- Atualização de informações
- Exclusão lógica
- Associação de professores e disciplinas
- Controle de especialidade
- Formatação automática de telefone e CPF

Gerenciamento de Turmas
- Controle de capacidade máxima
- Organização por série escolar
- Distribuição automática de alunos em turmas menos ocupadas
- Associação de disciplinas às turmas
- Consulta detalhada de disciplinas por turma

Gerenciamento de Disciplinas
- Cadastro de disciplinas
- Associação de disciplinas a professores
- Associação de disciplinas às turmas

Sistema de Notas e Boletim
- Lançamento de notas por:
  - disciplina
  - aluno
  - bimestre
  - tipo de avaliação
- Controle de tipos de nota:
  - prova
  - trabalho
  - atividade
- Validação de notas e bimestres
- Geração automática de boletim
- Cálculo de médias por bimestre
- Cálculo de média final
- Definição  automática de situação do aluno;
  - APROVADO
  - REPROVADO

Regras de Negócio Implementadas
- Alunos não podem receber notas de disciplinas não vinculadas à sua turma
- Controle automático de capacidade de turmas
- Distribuição inteligente de alunos entre turmas
- Validação de notas entre 0 e 10
- Validação de bimestres entre 1 e 4
- Tratamento padronizado de exceções da API

Integração Externa
- Integração com ViaCep para preechimento automático de endereço a partir do CEP

Recursos Técnicos
- API REST seguindo arquitetura em camadas
- Uso de DTOs para tranferência de dados
- Paginação de resultados
- Tratamento global de erros
- Modelagem relacional com JPA/Hibernate
- Relacionamentos:
  - OneToMany
  - ManyToOne
  - ManyToMany
- Organização modular do sistema

  

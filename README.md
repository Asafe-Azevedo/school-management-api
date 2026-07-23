# 📚 School Management API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3-red)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![JUnit5](https://img.shields.io/badge/JUnit-5-success)
![License](https://img.shields.io/badge/license-MIT-green)

RESTful API developed with Java and Spring Boot for school management.

The project was created to simulate a real educational management system, applying software engineering best practices, layered architecture, SOLID principles, validation, exception handling, relational database modeling, and automated testing.

It supports management of students, teachers, classes, subjects and grades, including automatic class assignment and report card generation.
----------------------------------------------------------------------------------------------------------------------

## Table of Contents

- [Technologies](#technologies)
- [Features](#features)
- [Business Rules](#business-rules)
- [Architecture](#architecture)
- [Running the Project](#running-the-project)
- [Tests](#tests)
- [API Endpoints](#api-endpoints)
- [Roadmap](#roadmap)
- [Author](#author)

## Technologies

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- Hibernate
- Spring Validation
- Spring Web
- Spring Security
- MySQL
- Maven
- Flyway Migration
- Lombok
- WebClient
- ViaCEP API
- JUnit 5
- Mockito
- MockMvc
- H2 Database

## Features

### Student Management

- Student registration
- Update student information
- Logical deletion
- Pagination
- Search by ID
- Automatic class assignment
- CPF validation
- School grade control

---

### Teacher Management

- Teacher registration
- Teacher specialization
- Subject assignment
- Logical deletion
- CPF validation
- Phone formatting

---

### Subject Management

- Create subjects
- Link teachers
- Link classes

---

### Class Management

- Maximum capacity control
- Automatic student distribution
- School grade organization
- Subject assignment

---

### Grade System

- Register grades
- Quarterly report cards
- Final average calculation
- Student status
  - APPROVED
  - FAILED

## Business Rules

The API enforces several business rules to ensure data consistency.

Examples:

- Students cannot receive grades for subjects outside their class.
- Classes have maximum capacity.
- Students are automatically assigned to the least occupied class.
- Grades must be between 0 and 10.
- School terms are limited from 1 to 4.
- Logical deletion prevents accidental data loss.
- Standardized exception handling.

## Architecture

The project follows a layered architecture.


    Controller
    
    ↓
    
    DTO
    
    ↓
    
    Service
    
    ↓
    
    Repository
    
    ↓
    
    Database
Each layer has a single responsibility, improving maintainability, scalability and testability.
---------------------------------------------------------------------------------------------------------------
### Structure

```text
src
├── main
│   ├── java
│   │   └── com.school.api
│   │       ├── aluno
│   │       ├── professores
│   │       ├── turma
│   │       ├── endereco
│   │       ├── security
│   │       ├── infra
│   │       └── util
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.properties
└── test
    └── java
        └── com.school.api
            └── integration
                ├── controller
                ├── repository
                ├── service
                ├── security
                └── support
```
### Package Overview

| Package | Responsibility |
|---------|----------------|
| `aluno` | Student domain (controller, service, repository, entity, DTOs). |
| `professores` | Teacher management. |
| `turma` | Class management and automatic student allocation. |
| `disciplina` | Subject management. |
| `nota` | Grade management and report card generation. |
| `security` | Authentication, authorization and JWT. |
| `infra` | Global exception handling, Swagger configuration and shared infrastructure. |
| `util` | Shared utility classes. |
| `integration` | Repository, service and controller integration tests. |            
                  
              
## Running the project

### Clone the repository

```bash
git clone https://github.com/Asafe-Azevedo/school-management-api.git
```

### Enter the folder

```bash
cd school-management-api
```

### Configure MySQL

Create a database:

```sql
CREATE DATABASE school_management;
```

### Configure application.properties

```properties
spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...
```

### Run

```bash
mvn spring-boot:run
```


## Tests

The project includes:

- Repository Integration Tests
- Service Unit Tests
- Controller Integration Tests
- MockMvc Tests
- Mockito
- H2 Database

The tests validate:

- Business rules
- CRUD operations
- Exception handling
- API responses
- Database persistence

## API Endpoints
### Students
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/students` | List students with pagination. |
| GET | `/students/{id}` | Find student by ID. |
| POST | `/students` | Create a student. |
| PUT | `/students/{id}` | Update a student. |
| DELETE | `/students/{id}` | Logically delete a student. |
#### POST EXAMPLE
    {
      "nome": "Maria Eduarda Silva",
      "email": "maria.silva@gmail.com",
      "cpf": "12345678901",
      "dataNascimento": "2010-05-20",
      "nomeResponsavel": "Carlos Silva",
      "telefoneResponsavel": "110987654321",
      "serie": "PRIMEIRO_EM",
      "endereco": {
        "cep": "01001000",
        "logradouro": "Praça da Sé",
        "numero": "100",
        "bairro": "Sé",
        "localidade": "São Paulo",
        "uf": "SP",
        "complemento": "Apartamento 12"
      }
    }

### Teachers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/teachers` | List teachers. |
| GET | `/teachers/{id}` | Find teacher by ID. |
| POST | `/teachers` | Create a teacher. |
| PUT | `/teachers/{id}` | Update a teacher. |
| DELETE | `/teachers/{id}` | Logically delete a teacher. |

### Subjects
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/subjects` | List subjects. |
| GET | `/subjects/{id}` | Find subject by ID. |
| POST | `/subjects` | Create a subject. |
| PUT | `/subjects/{id}` | Update a subject's responsible teacher. |
| DELETE | `/subjects/{id}` | Logically delete a subject. |

### Grades
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/grades/reportCard/{studentId}` | Generate student report card by ID. |
| POST | `/grades` | Assign a grade. |

### Classes
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/classes/{classId}/subjects` | List subjects assigned to a class. |
| GET | `/classes/{id}` | Find class by ID. |
| POST | `/classes/{classId}/subjects/{subjectId}` | Add a subject to a class. |

## Roadmap

- Docker
- CI/CD
- Testcontainers
- Redis Cache

## Author

Asafe Azevedo

Java Backend Developer

GitHub:
https://github.com/Asafe-Azevedo

## Highlights

- Layered Architecture
- RESTful API
- Business Rules Enforcement
- DTO Pattern
- Global Exception Handling
- Database Migration with Flyway
- Integration with ViaCEP API
- Automated Tests
- Pagination
- Bean Validation

## License

This project is licensed under the MIT License.

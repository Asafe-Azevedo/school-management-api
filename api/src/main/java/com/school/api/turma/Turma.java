package com.school.api.turma;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.api.aluno.Aluno;
import com.school.api.aluno.disciplina.Disciplina;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Turma")
@Table(name = "turmas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    private Serie serie;

    @JsonIgnore
    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "turma_disciplinas",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas;
}


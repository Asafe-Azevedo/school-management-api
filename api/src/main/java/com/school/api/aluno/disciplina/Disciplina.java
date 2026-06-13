package com.school.api.aluno.disciplina;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.api.professores.Professor;
import com.school.api.turma.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Disciplina")
@Table(name = "disciplinas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @JsonIgnore
    @ManyToMany(mappedBy = "disciplinas")
    private List<Turma> turmas = new ArrayList<>();

    private Boolean ativo;

    public Disciplina(String nome, Professor professor){
        this.nome = nome;
        this.professor = professor;
        this.ativo = true;
    }

    public void atualizarProfessor(Professor professor){
        this.professor = professor;
    }

    public void excluir(){
        this.ativo = false;
    }
}

package com.school.api.aluno;

import com.school.api.aluno.dto.DadosAtualizacaoAlunos;
import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.endereco.Endereco;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;
import com.school.api.util.FormatadorUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "alunos")
@Entity(name = "Aluno")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    private LocalDate dataNascimento;
    private String nomeResponsavel;
    private String telefoneResponsavel;

    @Enumerated(EnumType.STRING)
    private Serie serie;

    public Aluno(DadosCadastroAlunos dadosCadastroAlunos, Endereco endereco, String cpf){
        String cpfFormatado = FormatadorUtils.formatarCpf(cpf);

        this.ativo = true;
        this.nome = dadosCadastroAlunos.nome();
        this.email = dadosCadastroAlunos.email();
        this.cpf = cpfFormatado;
        this.dataNascimento = dadosCadastroAlunos.dataNascimento();
        this.nomeResponsavel = dadosCadastroAlunos.nomeResponsavel();
        this.telefoneResponsavel = dadosCadastroAlunos.telefoneResponsavel();
        this.serie = dadosCadastroAlunos.serie();
        this.endereco = endereco;
    }

    public void excluir(){
        this.ativo = false;
    }

    public void definirTurma(Turma turma){
        if(turma == null){
            throw new IllegalArgumentException("Turma não pode ser nula");
        }
        this.turma = turma;
    }

    public void atualizarNome(String nome){
        this.nome = nome;
    }

    public void atualizarEmail(String email) {
        this.email = email;
    }

    public void atualizarTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public void atualizarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

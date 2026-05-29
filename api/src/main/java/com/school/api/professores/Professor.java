package com.school.api.professores;

import com.school.api.endereco.Endereco;
import com.school.api.professores.Especialidade;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "professores")
@Entity(name = "Professor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;


    public Professor(String nome, String email, String telefone, String cpf, Especialidade especialidade, Endereco endereco) {
        this.ativo = true;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.endereco = endereco;
    }
    public void atualizarNome(String nome) {
        this.nome = nome;
    }
    public void atualizarTelefone(String telefoneFormatado) {
        this.telefone = telefoneFormatado;
    }
    public void atualizarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public void excluir(){
        this.ativo = false;
    }
}

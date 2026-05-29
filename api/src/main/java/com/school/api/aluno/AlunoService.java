package com.school.api.aluno;

import com.school.api.aluno.dto.DadosAtualizacaoAlunos;
import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.aluno.dto.DadosDetalhamentoAluno;
import com.school.api.aluno.dto.DadosListagemAlunos;
import com.school.api.endereco.Endereco;
import com.school.api.endereco.EnderecoService;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.infra.erros.alunos.AlunoNaoEncontradoException;
import com.school.api.turma.Turma;
import com.school.api.turma.TurmaRepository;
import com.school.api.util.FormatadorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRespository repository;
    private final EnderecoService enderecoService;
    private final TurmaRepository turmaRepository;

    public AlunoService(AlunoRespository respository, EnderecoService enderecoService, TurmaRepository turmaRepository){
        this.repository = respository;
        this.enderecoService = enderecoService;
        this.turmaRepository = turmaRepository;
    }

    public Aluno cadastrar(DadosCadastroAlunos dadosCadastroAlunoss){


        String cpfFormatado = FormatadorUtils.formatarCpf(dadosCadastroAlunoss.cpf());

        if (repository.existsByCpf(cpfFormatado)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        DadosEndereco enderecoPreenchido =
                enderecoService.preencherEndereco(dadosCadastroAlunoss.endereco());

        Turma turma = turmaRepository.buscarTurmasOrdenadasPorSerieOrdenadas(dadosCadastroAlunoss.serie())
                .stream().filter(t -> t.getAlunos().size() < t.getCapacidade())
                .sorted((t1, t2) -> {
                    int limiteIdeal = 30;
                    int t1Score = t1.getAlunos().size() < limiteIdeal ? 0 : 1;
                    int t2Score = t2.getAlunos().size() < limiteIdeal ? 0 : 1;

                    if(t1Score != t2Score){
                        return Integer.compare(t1Score, t2Score);
                    }
                    return Integer.compare(t1.getAlunos().size(), t2.getAlunos().size());
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma turma disponível"));


        Aluno aluno = new Aluno(dadosCadastroAlunoss,
                new Endereco(enderecoPreenchido), cpfFormatado
        );
        aluno.definirTurma(turma);
        return repository.save(aluno);
    }

    public Page<DadosListagemAlunos> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(DadosListagemAlunos::new);
    }

    public DadosDetalhamentoAluno detalhar(Long id){
        Aluno aluno = repository.findById(id).orElseThrow(AlunoNaoEncontradoException::new);
        return new DadosDetalhamentoAluno(aluno);

    }

    public Aluno atualizar(Long id, DadosAtualizacaoAlunos dados) {
        Aluno aluno = repository.findById(id).orElseThrow(AlunoNaoEncontradoException::new);

        if (dados.nome() != null) {
            aluno.atualizarNome(dados.nome());
        }
        if (dados.email() != null) {
            aluno.atualizarEmail(dados.email());
        }
        if (dados.telefoneResponsavel() != null) {
            aluno.atualizarTelefoneResponsavel(dados.telefoneResponsavel());
        }
        if (dados.endereco() != null) {
            DadosEndereco enderecoPreenchido = enderecoService.preencherEndereco(dados.endereco());
            aluno.atualizarEndereco(new Endereco(enderecoPreenchido));
        }
        return aluno;
    }

    public void excluir(Long id){
        Aluno aluno = repository.getReferenceById(id);
        aluno.excluir();
    }
}

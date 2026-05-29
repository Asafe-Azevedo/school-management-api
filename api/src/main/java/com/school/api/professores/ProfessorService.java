package com.school.api.professores;

import com.school.api.infra.erros.NotFoundException;
import com.school.api.infra.erros.professores.ProfessorNaoEncontradoException;
import com.school.api.util.FormatadorUtils;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.endereco.Endereco;
import com.school.api.endereco.EnderecoService;
import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;
import com.school.api.professores.dto.DadosListagemProfessores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final EnderecoService enderecoService;

     public ProfessorService(ProfessorRepository repository, EnderecoService enderecoService){
         this.repository = repository;
         this.enderecoService = enderecoService;
     }

     public Professor cadastrar(DadosCadastroProfessores dadosCadastroProfessores){

         DadosEndereco enderecoPreenchido = enderecoService.preencherEndereco(dadosCadastroProfessores.endereco());

         String telefoneFormatado = FormatadorUtils.formatarTelefone(dadosCadastroProfessores.telefone());
         String cpfFormatado = FormatadorUtils.formatarCpf(dadosCadastroProfessores.cpf());

         Professor professor = new Professor(
                 dadosCadastroProfessores.nome(),
                 dadosCadastroProfessores.email(),
                 telefoneFormatado,
                 cpfFormatado,
                 dadosCadastroProfessores.especialidade(),
                 new Endereco(enderecoPreenchido)
         );

         return repository.save(professor);
     }

     public Page<DadosListagemProfessores> listar(Pageable pageable){
         return repository.findAllByAtivoTrue(pageable).map(DadosListagemProfessores::new);
     }

     public Professor atualizar(Long id, DadosAtualizacaoProfessores dadosAtualizacaoProfessores){
        Professor professor = repository.findById(id).orElseThrow(ProfessorNaoEncontradoException::new);

        if (dadosAtualizacaoProfessores.nome() != null){
            professor.atualizarNome(dadosAtualizacaoProfessores.nome());
        }
        if (dadosAtualizacaoProfessores.telefone() != null){
            String telefoneFormatado = FormatadorUtils.formatarTelefone(dadosAtualizacaoProfessores.telefone());
            professor.atualizarTelefone(telefoneFormatado);
        }
        if (dadosAtualizacaoProfessores.endereco() != null){
            DadosEndereco enderecoPreenchido = enderecoService.preencherEndereco(dadosAtualizacaoProfessores.endereco());
            professor.atualizarEndereco(new Endereco(enderecoPreenchido));
        }
        return professor;
     }
     public void excluir(Long id){
         Professor professor = repository.findById(id).orElseThrow(ProfessorNaoEncontradoException::new);
         professor.excluir();
     }
}


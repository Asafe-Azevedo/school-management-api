package com.school.api.endereco;

import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.util.CepUtils;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.endereco.dto.DtoViaCepResponse;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final CepService cepService;

    public EnderecoService(CepService cepService) {
        this.cepService = cepService;
    }

    public DadosEndereco preencherEndereco(DadosEndereco dadosEndereco) {

        String cepFormatado = CepUtils.formatarCep(dadosEndereco.cep());

        DtoViaCepResponse response = cepService.buscarCep(
                cepFormatado.replace("-", "")
        );

        if (response == null || response.logradouro() == null) {
            throw new RegraNegocioException("CEP inválido ou não encontrado");
        }

        return new DadosEndereco(
                cepFormatado,
                response.logradouro(),
                dadosEndereco.numero(),
                response.bairro(),
                response.localidade(),
                response.uf(),
                dadosEndereco.complemento()
        );
    }
}

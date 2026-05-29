package com.school.api.endereco;

import com.school.api.endereco.dto.DtoViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CepService {

    private final WebClient webClient;

    public CepService(WebClient webClient){
        this.webClient = webClient;
    }

    public DtoViaCepResponse buscarCep(String cep){
        return webClient
                .get()
                .uri("/{cep}/json/", cep)
                .retrieve()
                .bodyToMono(DtoViaCepResponse.class)
                .block();
    }
}

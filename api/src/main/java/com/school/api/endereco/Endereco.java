package com.school.api.endereco;

import com.school.api.endereco.dto.DadosEndereco;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    public Endereco(DadosEndereco endereco) {
        this.cep = endereco.cep();
        this.rua = endereco.logradouro();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.cidade = endereco.localidade();
        this.uf = endereco.uf();
        this.complemento = endereco.complemento();
    }
}

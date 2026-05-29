package com.school.api.professores.dto;

import com.school.api.endereco.dto.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProfessores( @NotNull
                                           Long id,
                                           String nome,
                                           String telefone,
                                           DadosEndereco endereco)
{

}

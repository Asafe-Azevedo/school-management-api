package com.school.api.integration.support.controller.aluno;

import com.school.api.integration.support.controller.BaseControllerIT;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class AlunoControllerHelper extends BaseControllerIT {

    protected static final String BASE_URL = "/alunos";


    protected String toJson(Object obj) throws Exception{
        return objectMapper.writeValueAsString(obj);
    }

    protected String cadastrarAlunoRetornandoJson(Object dto) throws Exception{
        return mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    protected Long extrairID(String json) throws Exception{
        return objectMapper.readTree(json)
                .get("id")
                .asLong();
    }
}

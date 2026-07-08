package com.school.api.integration.support.controller.professor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.api.integration.support.controller.BaseControllerIT;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class ProfessorControllerHelper extends BaseControllerIT {

    protected static final String BASE_URL = "/professores";

    protected String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    protected String cadastrarProfessorRetornandoJson(Object dto) throws Exception{
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

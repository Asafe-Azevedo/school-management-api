package com.school.api.integration.support.controller.disciplina;

import com.school.api.integration.support.controller.BaseControllerIT;

public abstract class DisciplinaControllerHelper extends BaseControllerIT {

    protected static final String BASE_URL = "/disciplinas";

    protected String toJson(Object obj) throws Exception{
        return objectMapper.writeValueAsString(obj);
    }
}

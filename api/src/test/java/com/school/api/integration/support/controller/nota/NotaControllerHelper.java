package com.school.api.integration.support.controller.nota;

import com.school.api.integration.support.controller.BaseControllerIT;

public abstract class NotaControllerHelper extends BaseControllerIT {

    protected static final String BASE_URL = "/notas";

    protected String toJson(Object obj) throws Exception{
        return objectMapper.writeValueAsString(obj);
    }
}

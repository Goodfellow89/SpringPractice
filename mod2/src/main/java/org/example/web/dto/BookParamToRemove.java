package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookParamToRemove {

    @NotEmpty
    String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

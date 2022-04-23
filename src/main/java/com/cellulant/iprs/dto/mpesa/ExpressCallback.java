package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExpressCallback {
    private Map<String, StkCallBack> Body;

    public Map<String, StkCallBack> getBody() {
        return Body;
    }

    @JsonProperty("Body")
    public void setBody(Map<String, StkCallBack> body) {
        Body = body;
    }


    @Override
    public String toString() {
        return ""+Body;
    }
}

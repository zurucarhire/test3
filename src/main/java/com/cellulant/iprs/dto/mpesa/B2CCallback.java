package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class B2CCallback {
    private Result result;

    public Result getResult() {
        return result;
    }

    @JsonProperty("Result")
    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "B2CCallback{" +
                "result=" + result +
                '}';
    }
}

package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultParameter {
    private String Key;
    private String Value;

    public String getKey() {
        return Key;
    }

    @JsonProperty("Key")
    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    @JsonProperty("Value")
    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "ResultParameter{" +
                "Key='" + Key + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}

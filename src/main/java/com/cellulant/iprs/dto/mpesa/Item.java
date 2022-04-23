package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private String Name;
    private String Value;

    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
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
        return "Item{" +
                "Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}

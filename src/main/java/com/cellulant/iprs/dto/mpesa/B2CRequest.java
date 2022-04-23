package com.cellulant.iprs.dto.mpesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B2CRequest {
    private int rentid;
    private String frommsisdn;
    private String tomsisdn;
    private int amount;
    private String extras = "nil";
}

package com.cellulant.iprs.dto.mpesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressRequest {
    private Long userID;
    private Long productID;
    private String msisdn;
    private double amount;
}

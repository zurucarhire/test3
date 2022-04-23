package com.cellulant.iprs.dto.mpesa;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class C2BRegister {
    private String OriginatorCoversationID;
    private String ResponseCode;
    private String ResponseDescription;
}

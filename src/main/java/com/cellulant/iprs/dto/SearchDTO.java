package com.cellulant.iprs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SearchDTO {
    private String requestType;
    private Long requestNumber;
    private Long requestSerialNumber;
}

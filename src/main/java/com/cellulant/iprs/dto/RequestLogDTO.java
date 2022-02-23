package com.cellulant.iprs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class RequestLogDTO {
    private String userName;
    private String requestType;
    private Long requestNumber;
    private Long requestSerialNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;

//    public RequestLogDTO(String userName, String requestType, Long requestNumber, Long requestSerialNumber, Date dateCreated) {
//        this.userName = userName;
//        this.requestType = requestType;
//        this.requestNumber = requestNumber;
//        this.requestSerialNumber = requestSerialNumber;
//        this.dateCreated = dateCreated;
//    }
}

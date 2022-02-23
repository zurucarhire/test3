package com.cellulant.iprs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class RefreshedLogDTO {
    private String requestType;
    private Long requestNumber;
    private Long requestSerialNumber;
    private Long customerArchiveID;
    private Long customerID;
    private String IDNumber;
    private String IDSerialNumber;
    private String firstName;
    private String otherName;
    private String surName;
    private String family;
    private String gender;
    private String ethnicGroup;
    private String occupation;
    private String pin;
    private String citizenship;
    private String placeOfBirth;
    private String placeOfLive;
    private String placeOfDeath;
    private String regOffice;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfDeath;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfIssue;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date passportExpiryDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateCreated;
    private String userName;
}

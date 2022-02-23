package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "customerarchive")
public class CustomerArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerArchiveID")
    private Long customerArchiveID;
    @Column(name = "customerID", nullable = false)
    private Long customerID;
    @Column(name = "requestLogID", nullable = false)
    private Long requestLogID;
    @Column(name = "requestType", columnDefinition = "VARCHAR(30) NOT NULL")
    private String requestType;
    @Column(name = "IDNumber", columnDefinition = "VARCHAR(30) NOT NULL")
    private String IDNumber;
    @Column(name = "IDSerialNumber", columnDefinition = "VARCHAR(30) NOT NULL")
    private String IDSerialNumber;
    @Column(name = "citizenship", columnDefinition = "VARCHAR(30) NOT NULL")
    private String citizenship;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "passportDateOfBirth")
    private Date passportDateOfBirth;
    @Column(name = "dateOfDeath")
    private Date dateOfDeath;
    @Column(name = "dateOfIssue")
    private Date dateOfIssue;
    @Column(name = "passportExpiryDate")
    private Date passportExpiryDate;
    @Column(name = "ethnicGroup", columnDefinition = "VARCHAR(200) NOT NULL")
    private String ethnicGroup;
    @Column(name = "family", columnDefinition = "VARCHAR(200) NOT NULL")
    private String family;
    @Column(name = "firstName", columnDefinition = "VARCHAR(100) NOT NULL")
    private String firstName;
    @Column(name = "otherName", columnDefinition = "VARCHAR(100) NOT NULL")
    private String otherName;
    @Column(name = "surName", columnDefinition = "VARCHAR(50) NOT NULL")
    private String surName;
    @Column(name = "gender", columnDefinition = "VARCHAR(20) NOT NULL")
    private String gender;
    @Column(name = "occupation", columnDefinition = "VARCHAR(100) NOT NULL")
    private String occupation;
    @Column(name = "pin", columnDefinition = "VARCHAR(20) NOT NULL")
    private String pin;
    @Column(name = "placeOfBirth", columnDefinition = "text NOT NULL")
    private String placeOfBirth;
    @Column(name = "placeOfDeath", columnDefinition = "text NOT NULL")
    private String placeOfDeath;
    @Column(name = "placeOfLive", columnDefinition = "text NOT NULL")
    private String placeOfLive;
    @Column(name = "regOffice", columnDefinition = "text NOT NULL")
    private String regOffice;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @Column(name = "dateModified")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateModified;

}

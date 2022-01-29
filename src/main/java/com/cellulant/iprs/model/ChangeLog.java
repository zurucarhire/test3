package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "changelogs")
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "changeLogID", nullable = false)
    private Long changeLogID;
    @Column(name = "clientID", nullable = false)
    private Long clientID;
    @Column(name = "app", nullable = false)
    private String app;
    @Column(name = "changeStateID", nullable = false)
    private int changeStateID;
    @Column(name = "moduleID", nullable = false)
    private int moduleID;
    @Column(name = "makerUserID", nullable = false)
    private int makerUserID;
    @Column(name = "makerNarration", nullable = false)
    private String makerNarration;
    @Column(name = "makerIpAddress", nullable = false)
    private String makerIpAddress;
    @Column(name = "makerSessionID", nullable = false)
    private String makerSessionID;
    @Column(name = "checkerUserID", nullable = false)
    private int checkerUserID;
    @Column(name = "checkerNarration", nullable = false)
    private String checkerNarration;
    @Column(name = "checkerIpAddress", nullable = false)
    private String checkerIpAddress;
    @Column(name = "checkerSessionID", nullable = false)
    private String checkerSessionID;
    @Column(name = "changeTrackerKey", nullable = false)
    private String changeTrackerKey;
    @Column(name = "hostName", nullable = false)
    private String hostName;
    @Column(name = "insertedBy", nullable = false)
    private int insertedBy;
    @Column(name = "updatedBy", nullable = false)
    private int updatedBy;
    @Column(name = "dateChecked", nullable = false)
    private Timestamp dateChecked;
    @Column(name = "dateModified", nullable = false)
    private Timestamp dateModified;
    @Column(name = "dateCreated", nullable = false)
    private Timestamp dateCreated;
}

//package com.cellulant.iprs.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "groups")
//public class Group {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "groupID", nullable = false)
//    private Long groupID;
//    @Column(name = "groupName", nullable = false)
//    private String groupName;
//    @Column(name = "description", nullable = false)
//    private String description;
//    @Column(name = "active", nullable = false)
//    private int active;
//    @Column(name = "insertedBy", nullable = false)
//    private int insertedBy;
//    @Column(name = "updatedBy", nullable = false)
//    private int updatedBy;
//    @Column(name = "dateCreated", nullable = false)
//    private Timestamp dateCreated;
//    @Column(name = "dateModified", nullable = false)
//    private Timestamp dateModified;
//}

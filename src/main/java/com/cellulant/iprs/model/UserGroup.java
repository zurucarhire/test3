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
//@Table(name = "usergroups")
//public class UserGroup {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "userGroupID", nullable = false)
//    private Long userGroupID;
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
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "userID")
//    private User userID;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "role_id", referencedColumnName = "groupID")
//    private Role roleID;
//}

package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "userlogs")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userLogID", nullable = false)
    private Long userLogID;
    @Column(name = "userID", nullable = false)
    private Long userID;
    @Column(name = "loginTime")
    private Timestamp loginTime;
    @Column(name = "logoutTime")
    private Timestamp logoutTime;
    @Column(name = "loginIP", nullable = false)
    private String loginIP;
    @Column(name = "token")
    private String token;
    @Column(name = "sessionID")
    private String sessionID;
    @Column(name = "attemptsBeforeLogin", nullable = false)
    private int attemptsBeforeLogin;
    @Column(name = "insertedBy")
    private int insertedBy;
    @Column(name = "updatedBy")
    private int updatedBy;
    @Column(name = "dateModified")
    private Timestamp dateModified;
    @Column(name = "dateCreated")
    private Timestamp dateCreated;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
//    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
//    private User user;
}

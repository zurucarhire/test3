package com.cellulant.iprs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private Long userID;
    @Column(name = "clientID")
    private Long clientID;
    @Column(name = "roleID")
    private int roleID;
    @Column(name = "userName", columnDefinition = "VARCHAR(50) NOT NULL")
    private String userName;
    @Column(name = "fullName", columnDefinition = "VARCHAR(120) NOT NULL")
    private String fullName;
    @Column(name = "emailAddress", columnDefinition = "VARCHAR(120) NOT NULL")
    private String emailAddress;
    @Column(name = "idNumber", columnDefinition = "VARCHAR(30) NOT NULL")
    private String idNumber;
    @Column(name = "msisdn", columnDefinition = "VARCHAR(30) NOT NULL")
    private String msisdn;
    @Column(name = "canAccessUi", columnDefinition = "VARCHAR(10) NOT NULL")
    private String canAccessUi;
    @Column(name = "password", columnDefinition = "VARCHAR(150) NOT NULL")
    private String password;
    @Column(name = "passwordAttempts")
    private int passwordAttempts;
    @Column(name = "passwordStatusID")
    private String passwordStatusID;
    @Column(name = "active")
    private String active;
    @ManyToMany(fetch = EAGER)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Collection<Role> roles = new ArrayList<>();
    @Column(name = "datePasswordChanged")
    private Timestamp datePasswordChanged;
    @Column(name = "dateActivated")
    private Timestamp dateActivated;
    @Column(name = "dateCreated")
    private Timestamp dateCreated;
    @Column(name = "dateModified")
    private Timestamp dateModified;
    @Column(name = "updatedBy")
    private int updatedBy;
    @Column(name = "createdBy")
    private int createdBy;

    @Column(name = "lastLoginDate")
    private Timestamp lastLoginDate;
    @Column(name = "lastLoginDisplayDate")
    private Timestamp lastLoginDisplayDate;
    @Column(name = "authorities")
    private String authorities;
    @Column(name = "isNotLocked")
    private int isNotLocked;
    @ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "clientID", referencedColumnName = "clientID", insertable = false, updatable = false)
    private Client client;
}

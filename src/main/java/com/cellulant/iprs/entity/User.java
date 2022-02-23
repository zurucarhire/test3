package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Long userID;
    @Column(name = "clientID")
    @NotNull
    private Long clientID;
    @Column(name = "roleID")
    @NotNull
    private long roleID;
    @Column(name = "userName", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotNull
    private String userName;
    @Column(name = "fullName", columnDefinition = "VARCHAR(120) NOT NULL")
    @NotNull
    private String fullName;
    @Column(name = "emailAddress", columnDefinition = "VARCHAR(120) NOT NULL")
    @NotNull
    private String emailAddress;
    @Column(name = "idNumber", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotNull
    private String idNumber;
    @Column(name = "msisdn", columnDefinition = "VARCHAR(30) NOT NULL")
    @NotNull
    private String msisdn;
    @Column(name = "canAccessUi", columnDefinition = "VARCHAR(10) NOT NULL")
    private String canAccessUi;
    @Column(name = "password", columnDefinition = "VARCHAR(150) NOT NULL")
    private String password;
    @Column(name = "passwordAttempts")
    private int passwordAttempts;
    @Column(name = "active", nullable = false)
    private int active;
    @ManyToMany(fetch = EAGER)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Collection<Role> roles = new ArrayList<>();
    @Column(name = "datePasswordChanged")
    private Date datePasswordChanged;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @Column(name = "dateModified")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateModified;
    @Column(name = "updatedBy")
    private Long updatedBy;
    @Column(name = "createdBy")
    private Long createdBy;
    @Column(name = "lastLoginDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date lastLoginDate;
    @Column(name = "isNotLocked")
    private int isNotLocked;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = EAGER)
    @JoinColumn(name = "clientID", referencedColumnName = "clientID", insertable = false, updatable = false)
    private Client client;
}

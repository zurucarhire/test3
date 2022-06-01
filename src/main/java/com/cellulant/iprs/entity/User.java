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
    @Column(name = "roleID")
    @NotNull
    private long roleID;
    @Column(name = "emailAddress", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String emailAddress;
    @Column(name = "fullName", columnDefinition = "VARCHAR(100) NOT NULL UNIQUE")
    @NotNull
    private String fullName;
    @Column(name = "businessName", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String businessName;
    @Column(name = "shopLocation", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String shopLocation;
    @Column(name = "phone", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String phone;
    @Column(name = "category", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String category;
    @Column(name = "status", nullable = false)
    @NotNull
    private int status;
    @Column(name = "profileImage", columnDefinition = "TEXT NOT NULL")
    @NotNull
    private String profileImage;
    @Column(name = "password", columnDefinition = "VARCHAR(150) NOT NULL")
    private String password;
    @ManyToMany(fetch = EAGER)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Collection<Role> roles = new ArrayList<>();
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "dateCreated")
    private Date dateCreated;
}

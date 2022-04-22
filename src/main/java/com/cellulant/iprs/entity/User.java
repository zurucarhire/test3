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
    @Column(name = "userName", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotNull
    private String userName;
    @Column(name = "fullName", columnDefinition = "VARCHAR(100) NOT NULL UNIQUE")
    @NotNull
    private String fullName;
    @Column(name = "emailAddress", columnDefinition = "VARCHAR(100) NOT NULL")
    @NotNull
    private String emailAddress;
    @Column(name = "password", columnDefinition = "VARCHAR(150) NOT NULL")
    private String password;
    @ManyToMany(fetch = EAGER)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Collection<Role> roles = new ArrayList<>();
}

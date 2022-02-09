package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID")
    @NotNull
    private int roleID;
    @Column(name = "roleName", columnDefinition = "VARCHAR(50) NOT NULL")
    private String roleName;
    @Column(name = "description",columnDefinition = "VARCHAR(150) NOT NULL")
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Column(name = "active", nullable = false)
    private int active;
    @Column(name = "insertedBy")
    private int insertedBy;
    @Column(name = "updatedBy")
    private int updatedBy;
    @Column(name = "dateCreated")
    private Timestamp dateCreated;
    @Column(name = "dateModified")
    private Timestamp dateModified;
}

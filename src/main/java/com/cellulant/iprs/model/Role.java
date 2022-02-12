package com.cellulant.iprs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Date;

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
    //@NotNull
    private Long roleID;
    @Column(name = "roleName", columnDefinition = "VARCHAR(50) NOT NULL")
    private String roleName;
    @Column(name = "roleAlias", columnDefinition = "VARCHAR(50) NOT NULL")
    private String roleAlias;
    @Column(name = "description",columnDefinition = "VARCHAR(150) NOT NULL")
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Column(name = "permissions",columnDefinition = "VARCHAR(250) NOT NULL")
    @NotBlank(message = "Permissions is mandatory")
    private String permissions;
    @Column(name = "active")
    private int active;
    @Column(name = "insertedBy")
    private Long insertedBy;
    @Column(name = "updatedBy")
    private Long updatedBy;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @Column(name = "dateModified")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateModified;
}

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
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionID", nullable = false)
    private Long permissionID;
    @Column(name = "moduleActionID", nullable = false)
    private Long moduleActionID;
    @Column(name = "description", nullable = false)
    private Long groupID;
    @Column(name = "active", nullable = false)
    private int active;
    @Column(name = "access", nullable = false)
    private int access;
    @Column(name = "insertedBy", nullable = false)
    private int insertedBy;
    @Column(name = "updatedBy", nullable = false)
    private int updatedBy;
    @Column(name = "dateCreated", nullable = false)
    private Timestamp dateCreated;
    @Column(name = "dateModified", nullable = false)
    private Timestamp dateModified;
}

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
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moduleID", nullable = false)
    private Long moduleID;
    @Column(name = "moduleGroupID", nullable = false)
    private Long moduleGroupID;
    @Column(name = "moduleName", nullable = false)
    private String moduleName;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "access", nullable = false)
    private int access;
    @Column(name = "active", nullable = false)
    private int active;
    @Column(name = "insertedBy", nullable = false)
    private int insertedBy;
    @Column(name = "updatedBy", nullable = false)
    private int updatedBy;
    @Column(name = "dateCreated", nullable = false)
    private Timestamp dateCreated;
    @Column(name = "dateModified", nullable = false)
    private Timestamp dateModified;
}

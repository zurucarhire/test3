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
@Table(name = "modulegroups")
public class ModuleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moduleGroupID", nullable = false)
    private Long moduleGroupID;
    @Column(name = "moduleGroupName", nullable = false)
    private String moduleGroupName;
    @Column(name = "fullModuleGroupName", nullable = false)
    private String fullModuleGroupName;
    @Column(name = "description", nullable = false)
    private String description;
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

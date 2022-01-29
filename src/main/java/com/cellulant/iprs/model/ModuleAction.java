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
@Table(name = "moduleactions")
public class ModuleAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moduleActionID", nullable = false)
    private Long moduleActionID;
    @Column(name = "moduleID", nullable = false)
    private Long moduleID;
    @Column(name = "entityActionID", nullable = false)
    private Long entityActionID;
    @Column(name = "action", nullable = false)
    private String action;
    @Column(name = "label", nullable = false)
    private String label;
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

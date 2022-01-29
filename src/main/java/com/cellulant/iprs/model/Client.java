package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;
    @Column(name = "clientName", nullable = false)
    private String clientName;
    @Column(name = "clientDesc")
    private String clientDesc;
    @Column(name = "clientLogo")
    private String clientLogo;
    @Column(name = "passwordExpiryAge")
    private int passwordExpiryAge;
    @Column(name = "active")
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

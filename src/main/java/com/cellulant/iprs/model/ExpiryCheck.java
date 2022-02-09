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
@Table(name = "expirycheck")
public class ExpiryCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expiryID")
    private Long expiryID;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "expiryPeriod", nullable = false)
    private int expiryPeriod;
    @Column(name = "active")
    private int active;
    @Column(name = "dateCreated")
    private Timestamp dateCreated;
    @Column(name = "dateModified")
    private Timestamp dateModified;
}

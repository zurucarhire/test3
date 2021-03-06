package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;
    @Column(name = "clientName", columnDefinition = "VARCHAR(100) NOT NULL UNIQUE")
    private String clientName;
    @Column(name = "clientDescription", columnDefinition = "VARCHAR(250) NOT NULL")
    private String clientDescription;
    @Column(name = "active", nullable = false)
    private int active;
    @Column(name = "createdBy")
    private Long createdBy;
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

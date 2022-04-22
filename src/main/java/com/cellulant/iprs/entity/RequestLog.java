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
@Table(name = "requestlogs")
public class RequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestLogID")
    private Long requestLogID;
    @Column(name = "requestType", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String requestType;
    @Column(name = "requestNumber", nullable = false)
    private Long requestNumber;
    @Column(name = "requestSerialNumber", nullable = false)
    private Long requestSerialNumber;
    @Column(name = "createdBy", nullable = false)
    private Long createdBy;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @Column(name = "dateModified")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateModified;

}

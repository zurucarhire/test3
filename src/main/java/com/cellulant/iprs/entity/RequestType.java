package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "requesttypes")
public class RequestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestTypeID")
    private Long requestTypeID;
    @Column(name = "requestTypeName", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotNull(message = "Please provide request name")
    private String requestTypeName;
    @Column(name = "active")
    @NotNull(message = "Please provide active")
    private int active;
    @Column(name = "createdBy")
    @NotNull(message = "Please provide createdBy")
    private Long createdBy;
    @Column(name = "updatedBy")
    @NotNull(message = "Please provide updatedBy")
    private Long updatedBy;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "dateModified")
    private Date dateModified;
}

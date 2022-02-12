package com.cellulant.iprs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "changelogs")
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "changeLogID", nullable = false)
    private Long changeLogID;
    @Column(name = "narration", nullable = false)
    private String narration;
    @Column(name = "insertedBy", nullable = false)
    private Long insertedBy;
    @Column(name = "dateCreated", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "insertedBy", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;
}

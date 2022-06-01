package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "experiences")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experienceID")
    private Long experienceID;
    @Column(name = "procedureID")
    private Long procedureID;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "title", columnDefinition = "VARCHAR(100) NOT NULL")
    private String title;
    @Column(name = "completed", columnDefinition = "VARCHAR(100) NOT NULL")
    private String completed;
    @Column(name = "cost", nullable = false)
    private Double cost;
    @Column(name = "thumbnail", columnDefinition = "TEXT NOT NULL")
    private String thumbnail;
    @Column(name = "description", columnDefinition = "TEXT NOT NULL")
    private String description;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @OneToMany(fetch = EAGER)
    @JoinColumn(name = "experienceID", referencedColumnName = "experienceID", insertable = false, updatable = false)
    private List<ExperienceComment> experienceComment;
}

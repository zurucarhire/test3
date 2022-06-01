package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionID")
    private Long questionID;
    @Column(name = "procedureID")
    private Long procedureID;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "thumbnail", columnDefinition = "TEXT NOT NULL")
    private String thumbnail;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
}

package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedureID")
    private Long procedureID;
    @Column(name = "procedureName", nullable = false)
    private String procedureName;
    @Column(name = "description", columnDefinition = "TEXT NOT NULL")
    private String description;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "subcategory", nullable = false)
    private String subCategory;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "subtype", nullable = false)
    private String subType;
    @Column(name = "photo", nullable = false)
    private String photo;
    @Column(name = "procedureDescription", nullable = false)
    private Double cost;
    @Column(name = "dateCreated")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER, targetEntity = Question.class)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "procedureID", referencedColumnName = "procedureID", insertable = false, updatable = false)
    private List<Question> question;
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER, targetEntity = Experience.class)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "procedureID", referencedColumnName = "procedureID", insertable = false, updatable = false)
    private List<Experience> experience;
}

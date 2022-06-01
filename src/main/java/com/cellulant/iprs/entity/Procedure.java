package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "procedures")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
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
    @Column(name = "cost", nullable = false)
    private Double cost;
    @Type(type = "jsonb")
    @Column(name = "city",columnDefinition = "jsonb", nullable = false)
    private String city;
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

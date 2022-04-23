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
@Builder
@Entity
@Table(name = "productcategory")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productCategoryID", nullable = false)
    private Long productCategoryID;
    @Column(name = "name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT NOT NULL")
    private String description;
    @Column(name = "thumbnail", columnDefinition = "TEXT NOT NULL")
    private String thumbnail;
    @Column(name = "dateCreated", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
}

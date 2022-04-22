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
@Table(name = "loginlogs")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loginLogID")
    private Long loginLogID;
    @Column(name = "userID", nullable = false)
    private Long userID;
    @Column(name = "loginTime")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date loginTime;
    @Column(name = "logoutTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date logoutTime;
    @Column(name = "loginIP", columnDefinition = "VARCHAR(20) NOT NULL")
    private String loginIP;
    @Column(name = "attemptsBeforeLogin", nullable = false)
    private int attemptsBeforeLogin;
    @ManyToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;
}

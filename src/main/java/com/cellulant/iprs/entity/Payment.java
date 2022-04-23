package com.cellulant.iprs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;
    private Long userID;
    private Long productID;
    private String transactionID;
    private double amount;
    private String mpesaexpressresponsecode;
    private String mpesaexpressresponsedescription;
    private String mpesaexpressmerchantrequestid;
    private String mpesaexpresscheckoutrequestid;
    private String mpesaexpressreceiptnumber;
    private String mpesaexpresstransactiondate;
    private String mpesaexpressamount;
    private String paymentstatus;
    private String status;
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp processingdate;
}

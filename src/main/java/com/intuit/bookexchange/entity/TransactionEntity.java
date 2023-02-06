package com.intuit.bookexchange.entity;


import com.intuit.bookexchange.util.RequestStatus;
import lombok.Data;

import javax.persistence.*;

@Table(name = "transaction")
@Entity
@Data
public class TransactionEntity {

    @Id
    @Column(name= "transactionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @Column(name = "borrowerId")
    private Integer borrowerId;

    @Column (name = "lenderId")
    private Integer lenderId;

    @Column (name = "itemId")
    private Integer bookId;

    @Column (name = "requestStatus")
    private RequestStatus requestStatus;


}

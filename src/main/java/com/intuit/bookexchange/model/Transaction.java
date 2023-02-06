package com.intuit.bookexchange.model;


import com.intuit.bookexchange.util.RequestStatus;
import lombok.Data;

@Data
public class Transaction {
    private Integer transactionId;
    private Integer borrowerId;
    private Integer lenderId;
    private Integer bookId;
    private RequestStatus requestStatus;
}

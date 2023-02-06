package com.intuit.bookexchange.VO;

import lombok.Data;
import lombok.Getter;

@Getter
public class BookExchangeRequestVO {

    private Integer lenderId;
    private Integer borrowerId;
    private Integer borrowerReqBookId;
    private Integer lenderReqBookId;
    private Integer pendingTransactionId;
}

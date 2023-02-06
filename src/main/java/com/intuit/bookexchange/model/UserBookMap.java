package com.intuit.bookexchange.model;

import lombok.Data;

@Data
public class UserBookMap {

    private Integer userId;
    private Integer bookId;
    private Boolean isExchangeable;
}

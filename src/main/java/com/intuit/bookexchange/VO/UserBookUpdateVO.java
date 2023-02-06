package com.intuit.bookexchange.VO;

import lombok.Data;
import lombok.Getter;


@Getter
public class UserBookUpdateVO {

    private Integer userId;
    private Integer bookId;
    private Boolean isExchangeable;
}

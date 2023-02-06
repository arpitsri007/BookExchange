package com.intuit.bookexchange.VO;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserBookStoreVO {

    private Integer userId;
    private Integer bookId;
    private String author;
    private String title;
    private String language;

}

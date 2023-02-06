package com.intuit.bookexchange.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
public class BookVO {
    private Integer bookId;
    private String title;
    private String author;
    private String language;
}

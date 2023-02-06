package com.intuit.bookexchange.VO;

import com.intuit.bookexchange.model.UserBookMap;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class BookAvailabilityResponseVO {

    private Integer borrowerUserId;
    private List<UserBookMap> lenderBook;
    private Integer requestedBookId;
    // add other response attributes - rating, reviews , quantity

}

package com.intuit.bookexchange.service;

import com.intuit.bookexchange.VO.BookSearchVO;
import com.intuit.bookexchange.model.UserBookMap;
import com.intuit.bookexchange.util.ActivityType;

import java.util.List;


public interface IUserBookService {

    List<UserBookMap> searchRequestedBooks(BookSearchVO bookSearchRequest);

    List<UserBookMap> searchUserBookIds(Integer userId);

    Boolean updateBookOwnerShip(Integer borrowerUserId, Integer lenderUserId, Integer borrowerBookId,
                                Integer lenderBookId, ActivityType activityType);


    void updateBookExchangeStatus(Integer userId, Integer updateReqBookId, Boolean isExchangeable);

    UserBookMap addUserBook(Integer userId, Integer bookId);
}

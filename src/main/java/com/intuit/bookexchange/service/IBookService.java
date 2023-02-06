package com.intuit.bookexchange.service;
import com.intuit.bookexchange.VO.BookSearchVO;
import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.model.UserBookMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBookService {

    Book addBook(Book book);

}

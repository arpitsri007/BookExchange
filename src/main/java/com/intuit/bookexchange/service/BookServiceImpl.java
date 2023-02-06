package com.intuit.bookexchange.service;

import com.intuit.bookexchange.entity.BookEntity;
import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.repository.IBookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public Book addBook(Book book) {

        // add book in bookEntity
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setLanguage(book.getLanguage());

        BookEntity bookEntitySaved = bookRepository.save(bookEntity);

        Book savedBook = new Book();
        BeanUtils.copyProperties(bookEntitySaved, savedBook);

        return savedBook;
    }


}

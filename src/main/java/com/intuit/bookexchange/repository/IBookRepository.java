package com.intuit.bookexchange.repository;

import com.intuit.bookexchange.entity.BookEntity;
import com.intuit.bookexchange.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Integer> {

    Optional<BookEntity> findByBookId(Integer bookId);
}

package com.intuit.bookexchange.entity;

import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.model.User;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Table(name = "userBookRating")
public class UserBookRatingEntity implements Serializable {

    @Id
    @Column(name = "userId")
    private Integer userId;

    @Id
    @Column(name = "bookId")
    private Integer bookId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    UserEntity user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bookId", insertable = false, updatable = false)
//    BookEntity book;

    Integer rating;

    String review;

    LocalDateTime localDateTime;

    public UserBookRatingEntity() {

    }

}

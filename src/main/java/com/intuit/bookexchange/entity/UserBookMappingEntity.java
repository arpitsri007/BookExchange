package com.intuit.bookexchange.entity;

import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@Table(name = "userBookMapping")
public class UserBookMappingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "userBookMappingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userBookMappingId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "bookId")
    private Integer bookId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    UserEntity user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bookId", insertable = false, updatable = false)
//    BookEntity book;


    @Column(name = "isExchangeable")
    private Boolean isExchangeable;

    public UserBookMappingEntity() {

    }


}

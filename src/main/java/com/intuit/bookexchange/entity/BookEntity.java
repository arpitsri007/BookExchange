package com.intuit.bookexchange.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String language;

//    @OneToMany(mappedBy = "book")
//    Set<UserBookRatingEntity> userBookRatingEntities;
//
//    @OneToMany(mappedBy = "book")
//    Set<UserBookMappingEntity> userBookMappingEntities;

}

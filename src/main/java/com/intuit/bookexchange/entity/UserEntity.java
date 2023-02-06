package com.intuit.bookexchange.entity;

import com.intuit.bookexchange.model.Book;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "User")
@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Integer userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "rewardPoint")
    private Integer rewardPoint;

    @Column(name = "userEmail")
    private String userEmail;
//
//    @OneToMany(mappedBy = "user")
//    Set<UserBookRatingEntity> bookRatingEntitySet;
//
//    @OneToMany(mappedBy = "user")
//    Set<UserBookMappingEntity> userBookMappingEntitySet;

}

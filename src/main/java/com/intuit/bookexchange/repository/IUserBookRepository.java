package com.intuit.bookexchange.repository;

import com.intuit.bookexchange.entity.BookEntity;
import com.intuit.bookexchange.entity.UserBookMappingEntity;
import com.intuit.bookexchange.entity.UserEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUserBookRepository extends JpaRepository<UserBookMappingEntity, Integer> {

    List<UserBookMappingEntity> findByUserId(Integer userId);

    List<UserBookMappingEntity> findByBookId(Integer bookId);

    UserBookMappingEntity findByUserIdAndBookId(Integer userId, Integer bookId);
}

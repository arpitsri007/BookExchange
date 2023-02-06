package com.intuit.bookexchange.repository;

import com.intuit.bookexchange.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

   Optional<UserEntity> findByUserEmail(String email);
   Optional<UserEntity> findByUserId(Integer userId);

}

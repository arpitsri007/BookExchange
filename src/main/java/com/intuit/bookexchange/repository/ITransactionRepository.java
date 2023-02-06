package com.intuit.bookexchange.repository;

import com.intuit.bookexchange.entity.TransactionEntity;
import com.intuit.bookexchange.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findByLenderId(Integer lenderId);

    Optional<TransactionEntity> findByTransactionId(Integer transactionId);

    Optional<TransactionEntity> findByBorrowerIdAndBookId(Integer borrowerId, Integer bookId);
}

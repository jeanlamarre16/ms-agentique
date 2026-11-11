package org.example.msaccount.repository;

import org.example.msaccount.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
    Optional<AccountTransaction> findById(Long id);

    List<AccountTransaction> findAccountTransactionById(String accountId);
}

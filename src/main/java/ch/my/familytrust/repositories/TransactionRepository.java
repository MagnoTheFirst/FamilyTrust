package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public class TransactionRepository {

    @Query("SELECT DISTINCT s FROM Transaction s WHERE s.TRANSACTION_STATUS = ?1")
    Transaction findByName(String name);

    @Query("SELECT s FROM Transaction s WHERE s.INVESTMENT_TYPE = ?1")
    Optional<Transaction> findByDate_of_purchase(LocalDateTime date_of_purchase);

    @Query("SELECT s FROM Transaction s WHERE s.author = ?1")
    Optional<Transaction> findByGuarantee(LocalDateTime guarantee);

}

package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT s FROM Transaction s WHERE s.name = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Transaction> findByNameActive(String name);

    @Query("SELECT s FROM Transaction s WHERE s.author = ?1 AND s.transactionStatus = 'ACTIVE'")
    List<Transaction> findByAuthorActive(String author);

    @Query("SELECT s FROM Transaction s WHERE s.investmentType = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Transaction> findByInvestmentType(INVESTMENT_TYPE investmentType);

    @Query("SELECT s FROM Transaction s WHERE s.name = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Transaction> findByDirectory(String name);

/*
    @Query("SELECT s FROM Transaction s WHERE s.INVESTMENT_TYPE = ?1")
    Optional<Transaction> findByDate_of_purchase(LocalDateTime date_of_purchase);
*/
}

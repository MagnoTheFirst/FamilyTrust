package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

/*
    @Query("SELECT s FROM Transaction s WHERE s.INVESTMENT_TYPE = ?1")
    Optional<Transaction> findByDate_of_purchase(LocalDateTime date_of_purchase);
*/
}

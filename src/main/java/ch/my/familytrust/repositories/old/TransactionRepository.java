package ch.my.familytrust.repositories.old;

import ch.my.familytrust.entities.old.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

/*
    @Query("SELECT s FROM Transaction s WHERE s.INVESTMENT_TYPE = ?1")
    Optional<Transaction> findByDate_of_purchase(LocalDateTime date_of_purchase);
*/
}

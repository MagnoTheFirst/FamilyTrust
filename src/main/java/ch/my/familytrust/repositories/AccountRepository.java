package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    // Finde alle Accounts, bei denen der User Owner ist
    @Query("SELECT a FROM Account a WHERE a.owner.userId = :userId")
    List<Account> findByOwnerId(@Param("userId") UUID userId);

    // Finde alle Accounts, die mit dem User geteilt wurden
    @Query("SELECT a FROM Account a JOIN a.sharedUsers u WHERE u.userId = :userId")
    List<Account> findSharedWithUser(@Param("userId") UUID userId);

    // Finde alle Accounts zu denen der User Zugriff hat (owner oder shared)
    @Query("SELECT DISTINCT a FROM Account a " +
            "LEFT JOIN a.sharedUsers u " +
            "WHERE a.owner.userId = :userId " +
            "OR u.userId = :userId")
    List<Account> findAllAccessibleAccounts(@Param("userId") UUID userId);

    // Finde alle Accounts zu denen der User Zugriff hat, gefiltert nach Bank
    @Query("SELECT DISTINCT a FROM Account a " +
            "LEFT JOIN a.sharedUsers u " +
            "WHERE (a.owner.userId = :userId OR u.userId = :userId) " +
            "AND a.bankName = :bankName")
    List<Account> findAllAccessibleAccountsByBank(
            @Param("userId") UUID userId,
            @Param("bankName") String bankName);

    // Zähle die Anzahl der Accounts pro User (owned + shared)
    @Query("SELECT COUNT(DISTINCT a) FROM Account a " +
            "LEFT JOIN a.sharedUsers u " +
            "WHERE a.owner.userId = :userId OR u.userId = :userId")
    Long countAccessibleAccounts(@Param("userId") UUID userId);

    // Summiere das Gesamtguthaben aller zugänglichen Accounts
    @Query("SELECT SUM(a.balance) FROM Account a " +
            "LEFT JOIN a.sharedUsers u " +
            "WHERE a.owner.userId = :userId OR u.userId = :userId")
    Double sumAccessibleAccountsBalance(@Param("userId") UUID userId);
}
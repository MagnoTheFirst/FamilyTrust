package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query("SELECT a FROM Account a WHERE a.ownerUserId = :userId")
    List<Account> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT a FROM Account a WHERE a.ownerUserId = ?1 AND a.active = true")
    List<Account> findByUserIdAndActiveTrue(UUID userId);

    List<Account> findByOwnerUserId(UUID ownerUserId);

    Optional<Account> findById(UUID id);

}
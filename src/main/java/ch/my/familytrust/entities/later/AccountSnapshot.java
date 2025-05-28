package ch.my.familytrust.entities.later;

import ch.my.familytrust.entities.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AccountSnapshot {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Account account;

    private LocalDateTime snapshotDate;
    private BigDecimal totalValue;
    private BigDecimal cashBalance;


}
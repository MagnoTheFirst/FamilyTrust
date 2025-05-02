package ch.my.familytrust.entities;

import ch.my.familytrust.entities.enums.TransactionTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    UUID transactionId;

    @ManyToOne
    Account account;
    TransactionTypes transactionType;
    String comment;
    @OneToOne
    Asset asset;
    BigDecimal amount;

    LocalDateTime transactionTime;

}

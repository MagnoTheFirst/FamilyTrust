package ch.my.familytrust.entities;

import ch.my.familytrust.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountTransactionId;
    Long sourceAccountId;

    UUID assetId;

    String comment;
    LocalDateTime transactionDate;
    TransactionType transactionType;
    Double amount;

}

package ch.my.familytrust.entities;

import ch.my.familytrust.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Getter
@Setter
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountTransactionId;

    Long sourceAccountId;

    UUID assetId;

    String comment;
    LocalDateTime transactionDate;
    TransactionType transactionType;
    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}

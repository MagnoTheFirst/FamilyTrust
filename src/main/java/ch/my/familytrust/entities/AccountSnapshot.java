package ch.my.familytrust.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 *
 * A Snapshot of the account must be done once per day
 * */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSnapshot {

    @Id
    UUID snapshotId;

    @ManyToOne
    Account account;

    LocalDateTime snapshotTime;

    BigDecimal balance;


    private void setBalance(BigDecimal balance) {
        balance = account.calculateBalance();
    }


}

package ch.my.familytrust.entities;


import ch.my.familytrust.enums.CashflowType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountCashFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    LocalDateTime cashFlowDate;

    BigDecimal cashFlowAmount;

    CashflowType cashFlowType;

    String comment;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public AccountCashFlow(LocalDateTime cashFlowDate, BigDecimal cashFlowAmount, CashflowType cashFlowType, String comment) {
        if(cashFlowAmount == null){
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (cashFlowDate == null) {
            cashFlowDate = LocalDateTime.now();
        }
        this.cashFlowDate = cashFlowDate;
        this.cashFlowAmount = cashFlowAmount;
        this.cashFlowType = cashFlowType;
        this.comment = comment;
    }

    public AccountCashFlow(BigDecimal cashFlowAmount, CashflowType cashFlowType, String comment) {
        this(null, cashFlowAmount, cashFlowType, comment);
    }



}

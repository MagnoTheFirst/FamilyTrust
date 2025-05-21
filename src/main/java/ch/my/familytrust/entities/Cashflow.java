package ch.my.familytrust.entities;

import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.enums.CurrencyType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.type.descriptor.java.CurrencyJavaType;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

/**
 * Can either be an deposit or an withrawal or an transaction to another account.
 * */
public class Cashflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long caschflowId;

    LocalDateTime cashflowDate;
    @NotNull
    UUID sourceAccountId;
    UUID targetAccountId;
    @NotNull
    CashflowType cashflowType;
    @NotNull
    Double amount;

    String description;




}

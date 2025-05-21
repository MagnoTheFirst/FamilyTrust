package ch.my.familytrust.entities;

import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.Transaction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.HashMap;


/**
 * One Account can hold multiple assets.
 *
 * */
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String currencyCode = "CHF";

    @Transient
    Currency accountCurrency;

    String accountName;

    UUID ownerUserId;

    Boolean active;
    Boolean archived;

    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastAccess;

    @Transient
    BigDecimal balance;

    @NotNull
    BigDecimal availableMoney;

    @Transient
    BigDecimal totalAssetValue;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransaction> transactions = new ArrayList<>();

    /**
     * Payments, Dvidend Payment and Withdrawals
     * */

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountCashFlow> accountCashFlows = new ArrayList<>();

    //List<User> sharedWith;


    public Account(String currencyCode, String accountName, UUID ownerUserId) {

        this.currencyCode = currencyCode;
        this.accountCurrency  = Currency.getInstance(currencyCode);
        this.accountName = accountName;
        this.ownerUserId = ownerUserId;
        this.active = true;
        this.archived = false;
        this.createdDate =  LocalDateTime.now();
        this.lastAccess = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
        this.availableMoney = BigDecimal.ZERO;
        this.totalAssetValue = BigDecimal.ZERO;
    }

    /**
     *
     * Locale.CountryName
     * */
    void setCurrentCurrency(String currencyAbbreviation){
        accountCurrency = Currency.getInstance(currencyAbbreviation);
    }

    //TODO[] Must be implemented
    public String getBalance(){
        return null;
    }

    public void transfairMoney(Account targetAccount, Double amount){
        //Create transaction
        //Ger targetAccount
        //Rest ammount from Account Balance
        //Add Transaction to Account Transaction List
        //Add Transaction to Target Account Transaction List
    }

    public void makeAnAccountTransaction(TransactionType transactionType, Double amount){

        if(TransactionType.DEPOSIT.equals(transactionType)){
            //Create Transaction
            //Create Cashflow
            //Add to Cashflow list
            //Add ammount to Account Balance

        }
        else if(TransactionType.WITHDRAWAL.equals(transactionType)){
            //Create Transaction
            //Create Cashflow
            //Add to Cashflow list
            //Subtract ammount from Account Balance
        }
        else if (TransactionType.DIVIDEND_PAYMENT.equals(transactionType)){
            //Create Transaction
            //Create Cashflow
            //Add to Cashflow list
            //Add ammount to Account Balance
        }
        else{
            throw new IllegalArgumentException("TransactionType not supported");
        }
    }

    public void addAsset(Asset asset){
        //if(asset.name.equals()){
            //create AssetTransaction
            //Add to AssetTransaction List
            //Modify amount
            //Modify currentPrice
            //Modify assetBalance
       // }
    }

    public void accountCashFlowTransaction(AccountCashFlow accountCashFlow) {
        if (accountCashFlow == null || accountCashFlow.getCashFlowAmount() == null) {
            throw new IllegalArgumentException("AccountCashFlow or its amount is null");
        }

        if (availableMoney == null) {
            availableMoney = BigDecimal.ZERO;
        }

        switch (accountCashFlow.getCashFlowType()) {
            case DEPOSIT, DIVIDEND_PAYMENT -> {
                this.availableMoney = this.availableMoney.add(accountCashFlow.getCashFlowAmount());
            }
            case WITHDRAWAL -> {
                this.availableMoney = this.availableMoney.subtract(accountCashFlow.getCashFlowAmount());
            }
            default -> throw new IllegalArgumentException("CashflowType not supported");
        }

        // Vergiss nicht: CashFlow zur Liste hinzufügen
        this.accountCashFlows.add(accountCashFlow);
        accountCashFlow.setAccount(this); // wichtig für bidirektionales Mapping
    }

}

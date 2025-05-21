package ch.my.familytrust.entities;

import ch.my.familytrust.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
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
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String currencyCode = "CHF";
    @Transient
    Currency accountCurrency = Currency.getInstance(currencyCode);

    String accountName;

    UUID ownerUserId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    List<AccountTransaction> transactions;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastAccess;
    BigDecimal balance;
    BigDecimal totalCashflow;
    BigDecimal totalAssetValue;

    //List<User> sharedWith;

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

    public Asset createAsset(){
        return null;
    }
}

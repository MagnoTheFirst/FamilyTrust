package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString
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


    public Account() {
        this.accountCurrency  = Currency.getInstance(currencyCode);
        this.active = true;
        this.archived = false;
        this.createdDate =  LocalDateTime.now();
        this.lastAccess = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
        this.availableMoney = BigDecimal.ZERO;
        this.totalAssetValue = BigDecimal.ZERO;
    }

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


    public BigDecimal getBalance(){
        return this.balance;
    }


    public void addAsset(Asset asset){
        this.assets.add(asset);
    }

    //TODO[] complete this method.
    public void addAssetAndAssetTransaction(AssetTransaction transaction){
        boolean assetExists = false;
        for(Asset asset : this.assets){
            if(asset.getAssetId().equals(transaction.getAsset().getAssetId())){
                assetExists = true;
            }
        }
        if(!assetExists){
            addAsset(transaction.getAsset());
        }
    }

    public Account accountCashFlowTransaction(AccountCashFlow accountCashFlow) {
        if (accountCashFlow == null || accountCashFlow.getCashFlowAmount() == null) {
            throw new IllegalArgumentException("AccountCashFlow or its amount is null");
        }

        if (availableMoney == null) {
            this.availableMoney = BigDecimal.ZERO;
        }

        switch (accountCashFlow.getCashFlowType()) {
            case DEPOSIT, DIVIDEND_PAYMENT -> {
                System.out.println("PAYMENT OR DIVIDEND ");
                this.availableMoney = this.availableMoney.add(accountCashFlow.getCashFlowAmount());
            }
            case WITHDRAWAL -> {
                System.out.println("WITHDRAWAL ");
                this.availableMoney = this.availableMoney.subtract(accountCashFlow.getCashFlowAmount());
            }
            default -> throw new IllegalArgumentException("CashflowType not supported");
        }

        accountCashFlow.setAccount(this);
        this.accountCashFlows.add(accountCashFlow);
        return this;
    }

}

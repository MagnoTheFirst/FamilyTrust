package ch.my.familytrust.entities;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long assetId;

    @NotNull
    String name;

    String stockSymbol;

    @NotNull
    @Enumerated(EnumType.STRING)
    AssetType assetType;

    //TODO[] change currentPrice to last_known_price
    BigDecimal currentPrice;

    @NotNull
    Double quantity;

    @Transient
    BigDecimal assetBalance;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    private List<AssetTransaction> assetTransactions;

    Boolean active;
    Boolean archived;

    BigDecimal investedMoney;


    BigDecimal unrealizedProfitLoss;

    /*
    * TODO[] implement a coherent logic
    * When an asset is closed because all instances are sold,
    * realizedProfitLoss
    *
    * */
    BigDecimal realizedProfitLoss;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public Asset(Boolean archived, Boolean active, BigDecimal assetBalance, Double amount, BigDecimal currentPrice, AssetType assetType, String stockSymbol, String name) {
            this.archived = archived;
            this.active = active;
            this.assetBalance = assetBalance;
            this.quantity = amount;
            this.currentPrice = currentPrice;
            this.assetType = assetType;
            this.stockSymbol = stockSymbol;
            this.name = name;
            this.assetId = assetId;
            this.assetTransactions = new ArrayList<>();
            this.investedMoney = BigDecimal.ZERO;


    }

    public Asset(AssetDto dto) {
        this.archived = false;
        this.active = true;
        this.assetBalance = new BigDecimal(0);
        this.quantity = dto.quantityBigDecimal();
        this.currentPrice = dto.currentPrice();
        this.assetType = dto.assetType() == null ? AssetType.STOCK : dto.assetType();
        this.stockSymbol = dto.stockSymbol();
        this.name = dto.name();
        this.assetId = dto.assetId();
        this.assetTransactions = new ArrayList<>();
        this.account = new Account();
        this.account.setId(dto.accountId());
        this.investedMoney = dto.currentPrice().multiply(BigDecimal.valueOf(dto.quantityBigDecimal()));
    }

    public Asset() {

        this.assetTransactions = new ArrayList<>();
    }

    public void updateBalance() {

    }


    /*
    @Formula("(current_price * quantity) - investedMoney")
    private BigDecimal profitLoss;*/
/*
    @Formula("((current_price - buyPrice) / buyPrice) * 100")
    private BigDecimal profitLossPercentage;
*/
    public void addAssetTransaction(AssetTransaction transaction) {
        this.assetTransactions.add(transaction);
    }


    /*
    * //TODO[] This Method is an error
    * What I want to achieve is to make a balance that shows the invested amount of cash and the earned amount of cash through stock
    * sale.
    * */
    public BigDecimal getTransactionBalance() {
        BigDecimal assetBalance = new BigDecimal(0);
        for(AssetTransaction transaction : assetTransactions){
            BigDecimal tmp = transaction.getAssetTransactionBalance();
            if(tmp == null){
                System.out.println("ERROR getAssetBalance()");
            }
            else {
                System.out.println("SUCCESS " + transaction.getAssetTransactionBalance());
                assetBalance = assetBalance.add(tmp);
            }
        }
        System.out.println(assetBalance);
        return assetBalance;
    }


    public BigDecimal getAssetBalance() {
        BigDecimal balance = new BigDecimal(0);
        balance = balance.add(this.currentPrice.multiply(BigDecimal.valueOf(this.quantity)));
        return balance;
    }

    public BigDecimal getUnrealizedProfitLoss() {
        BigDecimal unrealizedProfitLoss = new BigDecimal(String.valueOf(this.currentPrice.multiply(BigDecimal.valueOf(this.quantity))));
        this.unrealizedProfitLoss = unrealizedProfitLoss.subtract(investedMoney);
        return this.unrealizedProfitLoss;
    }

    public BigDecimal getRealizedProfitLoss() {
        return this.realizedProfitLoss;
    }


}

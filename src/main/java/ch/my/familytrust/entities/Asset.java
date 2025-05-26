package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long assetId;

    @NotNull
    String name;

    String stockSymbol;
    @NotNull
    AssetType assetType;

    BigDecimal currentPrice;

    @NotNull
    Double quantity;

    @Transient
    BigDecimal assetBalance;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    private List<AssetTransaction> assetTransactions;

    Boolean active;
    Boolean archived;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Asset(Boolean archived, Boolean active, BigDecimal assetBalance, Double amount, BigDecimal currentPrice, AssetType assetType, String stockSymbol, String name) {
        if(assetTransactions.isEmpty()){
            this.archived = archived;
            this.active = active;
            this.assetBalance = assetBalance;
            this.quantity = amount;
            this.currentPrice = currentPrice;
            this.assetType = assetType;
            this.stockSymbol = stockSymbol;
            this.name = name;
            this.assetId = assetId;
        }

    }

    public void updateBalance() {

    }


    /* TODO[] will not work as expected
    @Formula("(current_price - buy_price) * quantity")
    private Double profitLoss;

    @Formula("((current_price - buyPrice) / buyPrice) * 100")
    private Double profitLossPercentage;
*/



}

package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long assetId;

    @NotNull
    String name;
    String stockSymbol;
    @NotNull
    private final AssetType assetType;

    /*@NotNull
    Double buyPrice;

    Double sellPrice;

     */
    BigDecimal currentPrice;

    @NotNull
    Double amount;

    BigDecimal assetBalance;

    List<AssetTransaction> assetTransactionList;


    /* TODO[] will not work as expected
    @Formula("(current_price - buy_price) * quantity")
    private Double profitLoss;

    @Formula("((current_price - buyPrice) / buyPrice) * 100")
    private Double profitLossPercentage;
*/

    public Asset(String name, String stockSymbol, AssetType assetType, BigDecimal currentPrice, Double amount, BigDecimal assetBalance) {
        this.name = name;
        this.stockSymbol = stockSymbol;
        this.assetType = assetType;
        this.currentPrice = currentPrice;
        this.amount = amount;
        this.assetBalance = assetBalance;
        this.assetTransactionList = assetTransactionList;
    }

    public void calculateProfitLoss(){
        double tmp_balance = 0;
        for(AssetTransaction assetTransaction : assetTransactionList){


        }
    }

    public void addAssetTransaction(Double amount, Double price, LocalDateTime transactionDate, AssetTransactionType assetTransactionType){
        AssetTransaction assetTransaction = new AssetTransaction(this, amount, price, transactionDate, assetTransactionType);
        Asset asset = assetTransaction.getAsset();
        this.assetBalance = asset.getAssetBalance();
        this.currentPrice = asset.getCurrentPrice();
        this.amount = assetTransaction.getAsset().getAmount();
        assetTransactionList.add(assetTransaction);
    }


}

package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AssetTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetTransactionId;

    @CreationTimestamp
    LocalDateTime transactionDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    AssetTransactionType assetTransactionType;
    Double quantity;
    BigDecimal price;
    BigDecimal assetTransactionBalance;
    String comment;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    public AssetTransaction(LocalDateTime transactionDate, AssetTransactionType assetTransactionType, Double quantity, BigDecimal price, BigDecimal assetTransactionBalance, String comment) {
        this.transactionDate = transactionDate;
        this.assetTransactionType = assetTransactionType;
        this.quantity = quantity;
        this.price = price;
        this.assetTransactionBalance = assetTransactionBalance;
        this.comment = comment;
    }

    public AssetTransaction(AssetTransactionType assetTransactionType, Double quantity, BigDecimal price, BigDecimal assetTransactionBalance, String comment) {
        this.assetTransactionType = assetTransactionType;
        this.quantity = quantity;
        this.price = price;
        this.assetTransactionBalance = assetTransactionBalance;
        this.comment = comment;
    }




}

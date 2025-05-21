package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AssetTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetTransactionId;

    LocalDateTime transactionDate;
    AssetTransactionType assetTransactionType;
    Double quantity;
    BigDecimal price;
    BigDecimal assetTransactionBalance;
    String comment;
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;


}

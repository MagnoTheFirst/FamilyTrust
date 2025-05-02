package ch.my.familytrust.entities;

import ch.my.familytrust.entities.enums.AssetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Asset {

    @Id
    UUID assetId;

    @ManyToOne
    Account account;

    @OneToOne
    User user;


    String name;
    String symbol;

    BigDecimal currentPrice;
    BigDecimal purchasePrice;
    BigDecimal sellingPrice;
    BigDecimal quantity;

    LocalDateTime boughtAt;
    LocalDateTime soldAt;

    AssetType assetType;

    String comment;

}

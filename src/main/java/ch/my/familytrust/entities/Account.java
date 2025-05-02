package ch.my.familytrust.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    UUID accountId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    String name;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AccountSnapshot> accountSnapshots= new ArrayList<>();


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets = new ArrayList<>();


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions= new ArrayList<>();

    String decription;

    BigDecimal balance;
    BigDecimal cashInStocks;


    BigDecimal calculateBalance(){
        BigDecimal balance = BigDecimal.ZERO;
        for(Asset asset : assets){
            balance = balance.add(asset.getQuantity().multiply(asset.getSellingPrice()));
        }
        return balance;
    }

    BigDecimal calculateCashInStocks(){
        BigDecimal cashInStocks = BigDecimal.ZERO;
        for (Asset asset : assets) {
            cashInStocks = cashInStocks.add(asset.getQuantity().multiply(asset.getPurchasePrice()));
        }
        return cashInStocks;
    }



}

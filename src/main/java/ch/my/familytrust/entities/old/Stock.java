package ch.my.familytrust.entities.old;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Stock extends Transaction {


    public Stock(Transaction transaction){
        //super(transaction.getName(), transaction.getComment());

        this.author = transaction.getAuthor();
        this.transactionStatus = transaction.getTransactionStatus();
        this.transactionType = transaction.getTransactionType();
        this.investmentType = transaction.getInvestmentType();
        this.cash_in_price = transaction.getCash_in_price();
    }

}

package ch.my.familytrust.entities;

import ch.my.familytrust.enums.INVESTMENT_TYPE;
import ch.my.familytrust.enums.TAG;
import ch.my.familytrust.enums.TRANSACTION_STATUS;
import ch.my.familytrust.enums.TRANSACTION_TYPE;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends Item{

    @Hidden
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public UUID id = UUID.randomUUID();

    @Size(min = 3,max = 20)
    @Schema(description = "The name of the author of the transaction")
    public String author;

    @NotNull
    @Column(name = "INVESTMENT_TYPE")
    @Enumerated(EnumType.STRING)
    public INVESTMENT_TYPE investmentType;
    @NotNull
    @Column(name = "TRANSACTION_STATUS")
    @Enumerated(EnumType.STRING)
    public TRANSACTION_STATUS transactionStatus;

    @NotNull
    @Column(name = "TRANSACTION_TYPE")
    @Enumerated(EnumType.STRING)
    public TRANSACTION_TYPE transactionType;

    LocalDateTime date = LocalDateTime.now();

    @Column(name = "CASH_IN_PRICE")
    public Double cash_in_price;

    @Column(name = "CASH_OUT_PRICE")
    public Double cash_out_price;

    @Column(name = "UNITS_BOUGHT")
    public Double units_bought;
    /*
    @Column(name = "UNITS_SOLD")
    public Double units_sold;
*/
    @Column(name = "PRICE_PER_UNIT_BUY")
    public Double price_per_unit_buy;
    @Column(name = "PRICE_PER_UNIT_SELL")
    public Double price_per_unit_sell;

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;

    public Transaction(String name, String comment, String author, TRANSACTION_TYPE transaction_type, TRANSACTION_STATUS transaction_status, INVESTMENT_TYPE investment_type){
        super(name, comment);
        this.author = author;
        this.transactionStatus = transaction_status;
        this.transactionType = transaction_type;
        this.investmentType = investment_type;
    }

    public Transaction(String name, String comment, String author, TRANSACTION_TYPE transaction_type, TRANSACTION_STATUS transaction_status, INVESTMENT_TYPE investment_type, Double cash_in_price){
        super(name, comment);
        this.author = author;
        this.transactionStatus = transaction_status;
        this.transactionType = transaction_type;
        this.investmentType = investment_type;
        this.cash_in_price = cash_in_price;
    }

    public Transaction(String name, String comment, Double amount, TAG tag, String author, INVESTMENT_TYPE investmentType, TRANSACTION_STATUS transactionStatus, TRANSACTION_TYPE transactionType, Double cash_in_price, Double units_bought) {
        super(name, comment, amount, tag);
        this.author = author;
        this.investmentType = investmentType;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.cash_in_price = cash_in_price;
        this.units_bought = units_bought;
    }


    public Transaction(String author, INVESTMENT_TYPE investmentType, TRANSACTION_STATUS transactionStatus, TRANSACTION_TYPE transactionType, Double cash_in_price, Double units_bought) {
        this.author = author;
        this.investmentType = investmentType;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.cash_in_price = cash_in_price;
        this.units_bought = units_bought;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", investmentType=" + investmentType +
                ", transactionStatus=" + transactionStatus +
                ", transactionType=" + transactionType +
                ", date=" + date +
                ", cash_in_price=" + cash_in_price +
                ", cash_out_price=" + cash_out_price +
                ", units_bought=" + units_bought +
                ", price_per_unit_buy=" + price_per_unit_buy +
                ", price_per_unit_sell=" + price_per_unit_sell +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", amount=" + amount +
                ", tag=" + tag +
                '}';
    }
}

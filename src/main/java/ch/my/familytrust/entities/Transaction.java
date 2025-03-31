package ch.my.familytrust.entities;

import ch.my.familytrust.enums.INVESTMENT_TYPE;
import ch.my.familytrust.enums.TRANSACTION_STATUS;
import ch.my.familytrust.enums.TRANSACTION_TYPE;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends Item{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public UUID id = UUID.randomUUID();
    public String author;
    @Column(name = "INVESTMENT_TYPE")
    @Enumerated(EnumType.STRING)
    public INVESTMENT_TYPE investmentType;
    @Column(name = "TRANSACTION_STATUS")
    @Enumerated(EnumType.STRING)
    public TRANSACTION_STATUS transactionStatus;

    @Enumerated(EnumType.STRING)
    public TRANSACTION_TYPE transactionType;

    LocalDateTime date = LocalDateTime.now();

    public Transaction(String name, String comment, String author, TRANSACTION_TYPE transaction_type, TRANSACTION_STATUS transaction_status, INVESTMENT_TYPE investment_type){
        super(name, comment);
        this.author = author;
        this.transactionStatus = transaction_status;
        this.transactionType = transaction_type;
        this.investmentType = investment_type;
    }

}

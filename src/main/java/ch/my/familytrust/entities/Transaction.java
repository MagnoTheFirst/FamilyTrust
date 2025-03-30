package ch.my.familytrust.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends Item{

    @Id
    public UUID id = UUID.randomUUID();

    public String author;
    public Enum INVESTMENT_TYPE;
    public Enum TRANSACTION_STATUS;
    public Enum TRANSACTION_TYPE;


    public Transaction(LocalDateTime date, String name, String comment, String author, Enum transaction_type, Enum transaction_status, Enum investment_type){
        super(date, name, comment);
        this.author = author;
        this.TRANSACTION_STATUS = transaction_status;
        this.TRANSACTION_TYPE = transaction_type;
        this.INVESTMENT_TYPE = investment_type;
    }

}

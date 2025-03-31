package ch.my.familytrust.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Payment extends Item{
    @Enumerated(EnumType.STRING)
    Enum PAYMENT_TYPE;
    String author;
    String account;

    @Id
    UUID id = UUID.randomUUID();

    public Payment(LocalDateTime date, String name, String comment, Enum PAYMENT_TYPE, String author, String account) {
        super(name, comment);
        this.PAYMENT_TYPE = PAYMENT_TYPE;
        this.author = author;
        this.account = account;
    }


}

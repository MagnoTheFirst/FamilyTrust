package ch.my.familytrust.entities;

import ch.my.familytrust.enums.PAYMENT_TYPE;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Payment extends Item{
    @Column(name = "PAYMENT_TYPE")
    @Enumerated(EnumType.STRING)
    PAYMENT_TYPE paymentType;
    String author;
    String account;

    @Id
    UUID id = UUID.randomUUID();

    public Payment(String name, String comment, PAYMENT_TYPE paymentType, String author, String account) {
        super(name, comment);
        this.paymentType = paymentType;
        this.author = author;
        this.account = account;
    }


}

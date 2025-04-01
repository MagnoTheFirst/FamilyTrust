package ch.my.familytrust.entities;

import ch.my.familytrust.enums.PAYMENT_TYPE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Payment{
    @Id
    UUID id = UUID.randomUUID();
    @Column(name = "PAYMENT_TYPE")
    @Enumerated(EnumType.STRING)
    public PAYMENT_TYPE paymentType;
    public String author;
    public Double amount;
    public String comment;
    @OneToOne
    @JoinColumn(name = "account_id")
    public Account account;



    public Payment(Double amount, String comment, PAYMENT_TYPE paymentType, String author, Account account) {
        this.paymentType = paymentType;
        this.author = author;
        this.account = account;
        this.comment = comment;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentType=" + paymentType +
                ", author='" + author + '\'' +
                ", account='" + account + '\'' +
                ", comment='" + comment + '\'' +
                ", amount=" + amount +
                '}';
    }
}

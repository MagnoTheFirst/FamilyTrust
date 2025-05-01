package ch.my.familytrust.entities.new_code;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID depositId;
    @OneToOne
    @JoinColumn(name = "account_id")
    Account account;
    String comment;
    Double amount;
    String autor;
    LocalDateTime depositDate;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}

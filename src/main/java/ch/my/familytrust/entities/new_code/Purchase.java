package ch.my.familytrust.entities.new_code;

import ch.my.familytrust.entities.old.Stock;
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
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID purchaseId;
    @OneToOne
    Stock stock;
    @OneToOne
    Account account;
    LocalDateTime purchaseDate;
    Double amount;
    String comment;


}

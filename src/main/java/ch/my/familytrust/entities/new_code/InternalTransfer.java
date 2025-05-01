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
public class InternalTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID transferId;

    @ManyToOne
    Account fromAccount;

    @ManyToOne
    Account toAccount;

    LocalDateTime timestamp;

    String comment;
    Double amount;


}

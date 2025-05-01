package ch.my.familytrust.entities.new_code;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.UUID;

public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID withDrawalId;

    @OneToOne
    Account account;

    Double amount;
    String comment;

}

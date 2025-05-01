package ch.my.familytrust.entities.new_code;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID recordId= UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    LocalDate recordDate;

    Double accountBalance;



}

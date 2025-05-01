package ch.my.familytrust.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    public UUID recordId= UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    LocalDate recordDate;

    Double accountBalance;



}

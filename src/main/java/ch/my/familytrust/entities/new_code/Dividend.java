package ch.my.familytrust.entities.new_code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID difidendId;
    Double amount;
    LocalDate date;
    String stockName;

}

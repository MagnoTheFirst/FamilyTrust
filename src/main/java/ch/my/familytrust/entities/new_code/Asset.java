package ch.my.familytrust.entities.new_code;

import ch.my.familytrust.entities.old.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Asset {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    UUID assetId;
    String name;
    String description;
    INVESTMENT_TYPE type;
    String symbol;



}

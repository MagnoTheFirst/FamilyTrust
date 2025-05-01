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
public class AccountSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID snapshotId;
    Double ammount;
    @OneToOne
    Account account;
    LocalDateTime createdAt;
}

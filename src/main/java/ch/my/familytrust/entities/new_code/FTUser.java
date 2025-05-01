package ch.my.familytrust.entities.new_code;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class FTUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID userID;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    List<Account> accounts;
}

package ch.my.familytrust.entities;

import ch.my.familytrust.entities.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    UUID userId;

    String username;

    String password;

    @Email
    String email;

    LocalDateTime created;
    LocalDateTime lastLogin;

    Boolean active;

    Role role;


}

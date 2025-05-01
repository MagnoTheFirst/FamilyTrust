package ch.my.familytrust.entities;

import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

public class User {

    @Id
    UUID userID = UUID.randomUUID();
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    List<Account> accounts;
}

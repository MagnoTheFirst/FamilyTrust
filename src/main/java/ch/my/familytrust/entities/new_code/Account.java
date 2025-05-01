package ch.my.familytrust.entities.new_code;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    public UUID id = UUID.randomUUID();

    public String name;

    public FTUser user;

    public String description;

    public LocalDate creationDate;


    //TODO[] implement owner once user management is implemented
}

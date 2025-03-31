package ch.my.familytrust.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class Item {

    public LocalDateTime date = LocalDateTime.now();
    public String name;
    public String comment;


    public Item(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }


}

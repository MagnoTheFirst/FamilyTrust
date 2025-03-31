package ch.my.familytrust.entities;

import ch.my.familytrust.enums.TAG;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Item {

    public LocalDateTime date = LocalDateTime.now();
    public String name;
    public String comment;
    public Double amount;
    public TAG tag;

    public Item(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public Item(String name, String comment, Double amount) {
        this.name = name;
        this.comment = comment;
        this.amount = amount;
    }

    public Item(String name, String comment, Double amount, TAG tag) {
        this.name = name;
        this.comment = comment;
        this.amount = amount;
        this.tag = tag;
    }

}

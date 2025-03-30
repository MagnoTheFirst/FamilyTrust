package ch.my.familytrust.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    public LocalDateTime date;
    public String name;
    public String comment;



}

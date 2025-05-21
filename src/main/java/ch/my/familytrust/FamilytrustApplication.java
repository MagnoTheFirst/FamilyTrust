package ch.my.familytrust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class FamilytrustApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(FamilytrustApplication.class, args);

    }

}

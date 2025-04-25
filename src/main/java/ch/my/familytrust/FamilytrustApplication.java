package ch.my.familytrust;

import ch.my.familytrust.services.StockMarketService;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FamilytrustApplication {


    public static void main(String[] args) throws IOException {

        SpringApplication.run(FamilytrustApplication.class, args);

    }

}

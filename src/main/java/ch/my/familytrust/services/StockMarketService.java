package ch.my.familytrust.services;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockMarketService {
    @Value("${local.rapid-api-key}")
    String API_KEY;

    public StockMarketService() {
    }


    public void getStockPrice(String stockIndex) throws IOException {
        System.out.println(API_KEY);
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://yahoo-finance127.p.rapidapi.com/finance-analytics/"+stockIndex)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "yahoo-finance127.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();
        client.close();
    }


}

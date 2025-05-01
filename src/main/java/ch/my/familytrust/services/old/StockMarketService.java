package ch.my.familytrust.services.old;

import org.springframework.stereotype.Service;

@Service
public class StockMarketService {/*
    @Value("${local.rapid-api-key}")
    String API_KEY;

    public StockMarketService() {
    }


    public Object getStockPrice(String stockIndex) throws IOException, ExecutionException, InterruptedException {
        System.out.println(API_KEY);
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        BoundRequestBuilder request = client.prepare("GET", "https://yahoo-finance127.p.rapidapi.com/finance-analytics/"+stockIndex)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "yahoo-finance127.p.rapidapi.com");
        Response response = request.execute().toCompletableFuture().get();

        // JSON-Parser (Jackson)
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getResponseBody());
        client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://yahoo-finance127.p.rapidapi.com/earnings/tsla")
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "yahoo-finance127.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.prepare("GET", "https://yahoo-finance127.p.rapidapi.com/finance-analytics/tsla")
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "yahoo-finance127.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();
        client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://yahoo-finance127.p.rapidapi.com/index-trend/aapl")
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "yahoo-finance127.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
        return json;

    }*/


}

package ch.my.familytrust.services.old;

import ch.my.familytrust.entities.old.Stock;
import ch.my.familytrust.repositories.old.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StockService {
    private final StockRepository transactionRepository;


    @Autowired
    public StockService(StockRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async
    public void createNewTransaction(Stock transaction){
        transactionRepository.saveAndFlush(transaction);
    }

    public List<Stock> getTransactions(){
        return transactionRepository.findAll();
    }

    @Async
    public List<Stock> getTransactionsOfOwner(String owner){
        return transactionRepository.findByAuthorActive(owner);
    }

    public void cancelTransaction(UUID transactionId){
        Stock transaction = transactionRepository.findById(transactionId).get();
        transactionRepository.delete(transaction);
    }


}

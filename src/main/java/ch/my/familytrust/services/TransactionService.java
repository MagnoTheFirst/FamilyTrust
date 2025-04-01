package ch.my.familytrust.services;

import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactinRepository;
    private final TransactionRepository transactionRepository;


    @Autowired
    public TransactionService(TransactionRepository transactinRepository, TransactionRepository transactionRepository) {
        this.transactinRepository = transactinRepository;
        this.transactionRepository = transactionRepository;
    }

    @Async
    public void createNewTransaction(Transaction transaction){
        transactinRepository.saveAndFlush(transaction);
    }

    public List<Transaction> getTransactions(){
        return transactinRepository.findAll();
    }

    @Async
    public List<Transaction> getTransactionsOfOwner(String owner){
        return transactinRepository.findByAuthorActive(owner);
    }

    public void cancelTransaction(UUID transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).get();
        transactionRepository.delete(transaction);
    }


}

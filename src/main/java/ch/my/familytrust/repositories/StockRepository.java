package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Stock;
import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {


    @Modifying
    @Query("update Stock t set t.amount = ?1 where t.id = ?2")
    int setAmmount(Double amount, UUID transactionId);

    @Query("SELECT s FROM Stock s WHERE s.author = ?1 AND s.transactionStatus = 'ACTIVE'")
    List<Stock> findByAuthorActive(String author);

    @Query("SELECT s FROM Stock s WHERE s.investmentType = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Stock> findByInvestmentType(INVESTMENT_TYPE investmentType);

    @Query("SELECT s FROM Stock s WHERE s.name = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Stock> findByDirectory(String name);

    @Query("SELECT s FROM Stock s WHERE s.name = ?1 AND s.transactionStatus = 'ACTIVE' ")
    List<Stock> findByInvestMentType(INVESTMENT_TYPE investmentType);

    @Query("SELECT s FROM Stock s WHERE s.investmentType = ?1 AND s.author = ?2 AND s.transactionStatus = 'ACTIVE' ")
    List<Stock> findByInvestMentTypeAndOwner(INVESTMENT_TYPE investmentType, String owner);

    @Query("SELECT s FROM Stock s WHERE s.investmentType = ?1 AND s.tag = ?2 AND s.transactionStatus = 'ACTIVE' ")
    List<Stock> findByInvestMentTypeAndTag(INVESTMENT_TYPE investmentType, String tag);


}

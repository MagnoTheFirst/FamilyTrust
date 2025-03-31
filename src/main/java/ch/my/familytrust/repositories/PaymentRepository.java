package ch.my.familytrust.repositories;

import ch.my.familytrust.entities.Payment;
import ch.my.familytrust.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT s FROM Payment s WHERE s.paymentType = 'ACCOUNT_TRANSFER' ")
    List<Payment> findAllIncommingPayments();

    @Query("SELECT s FROM Payment s WHERE s.paymentType = 'CASH_OUT' ")
    List<Payment> findAllCashOuts();


}

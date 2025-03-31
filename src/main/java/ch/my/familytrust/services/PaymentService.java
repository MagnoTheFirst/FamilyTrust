package ch.my.familytrust.services;

import ch.my.familytrust.entities.Payment;
import ch.my.familytrust.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    public final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public List<Payment> getAllIncommingPayments(){
        return paymentRepository.findAllIncommingPayments();
    }

    public List<Payment> getAllOutgoingPayments(){
        return paymentRepository.findAllCashOuts();
    }

    public void createNewPaymentRecord(Payment payment){
        paymentRepository.saveAndFlush(payment);
    }

}

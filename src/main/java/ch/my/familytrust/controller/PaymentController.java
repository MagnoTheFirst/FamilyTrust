package ch.my.familytrust.controller;

import ch.my.familytrust.entities.Payment;
import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.PAYMENT_TYPE;
import ch.my.familytrust.enums.TRANSACTION_TYPE;
import ch.my.familytrust.services.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment API", description = "API um eingehende und ausgehende Zahlungen zu erfassen")
@RestController
@RequestMapping(path="api/v1/family-trust/payments")
public class PaymentController {

    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPaymentRecord")
    public void test2(@RequestBody Payment payment){
        System.out.println(payment.toString());
        paymentService.createNewPaymentRecord(payment);
    }


    @GetMapping("/getAllPayments")
    public List<Payment> getAllPayments(){
        return paymentService.getAllIncommingPayments();
    }

    @GetMapping("/getAllCashOuts")
    public List<Payment> getAllCahOuts(){
        return paymentService.getAllOutgoingPayments();
    }

}

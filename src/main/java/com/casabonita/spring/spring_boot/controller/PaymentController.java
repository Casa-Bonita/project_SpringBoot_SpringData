package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.PaymentDTO;
import com.casabonita.spring.spring_boot.dto.SavePaymentDTO;
import com.casabonita.spring.spring_boot.entity.Payment;
import com.casabonita.spring.spring_boot.entity.Renter;
import com.casabonita.spring.spring_boot.service.PaymentService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MappingUtilsPayment mappingUtilsPayment;

    @GetMapping (value = "/payments")
    public List<PaymentDTO> showAllPayments(){

        List<Payment> allPayments = paymentService.findAll();

        return allPayments.stream()
                .map(mappingUtilsPayment::mapToPaymentDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/payments/{id}")
    public PaymentDTO getPayment(@PathVariable Integer id){

        return mappingUtilsPayment.mapToPaymentDTO(paymentService.findById(id));
    }

    @PostMapping(value = "/payments")
    public PaymentDTO addNewPayment(@RequestBody SavePaymentDTO savePaymentDTO){

        Payment payment = mappingUtilsPayment.mapToPayment(savePaymentDTO);

        paymentService.save(payment, savePaymentDTO.getAccountNumber());

        return mappingUtilsPayment.mapToPaymentDTO(paymentService.findById(payment.getId()));
    }

//    @PutMapping(value = "/payments")
//    public Payment updatePayment(@RequestBody Payment payment, @RequestParam String accountNumber){
//
//        paymentService.save(payment, accountNumber);
//
//        return payment;
//    }

    @DeleteMapping(value = "/payments/{id}")
    public String deletePayment(@PathVariable Integer id){

        Payment payment = paymentService.findById(id);

        if(payment == null){
            return "There is no Payment with id = " + id + " in DataBase.";
        }

        paymentService.deleteById(id);

        return "Payment with id = " + id + " was deleted.";
    }

}

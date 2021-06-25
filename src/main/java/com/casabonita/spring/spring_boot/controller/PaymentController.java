package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.PaymentDTO;
import com.casabonita.spring.spring_boot.dto.SavePaymentDTO;
import com.casabonita.spring.spring_boot.entity.Payment;
import com.casabonita.spring.spring_boot.service.PaymentService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsPayment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    private final PaymentService paymentService;
    private final MappingUtilsPayment mappingUtilsPayment;

    public PaymentController(PaymentService paymentService, MappingUtilsPayment mappingUtilsPayment) {
        this.paymentService = paymentService;
        this.mappingUtilsPayment = mappingUtilsPayment;
    }

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

        payment = paymentService.save(payment, savePaymentDTO.getAccountNumber());

        return mappingUtilsPayment.mapToPaymentDTO(payment);
    }

    @PutMapping(value = "/payments/{id}")
    public PaymentDTO updatePayment(@RequestBody SavePaymentDTO savePaymentDTO, @PathVariable Integer id){

        Payment payment = mappingUtilsPayment.mapToPayment(savePaymentDTO);

        payment.setId(id);

        payment = paymentService.save(payment, savePaymentDTO.getAccountNumber());

        return mappingUtilsPayment.mapToPaymentDTO(payment);
    }

    @DeleteMapping(value = "/payments/{id}")
    public String deletePayment(@PathVariable Integer id){

        paymentService.deleteById(id);

        return "Payment with id = " + id + " was deleted.";
    }
}

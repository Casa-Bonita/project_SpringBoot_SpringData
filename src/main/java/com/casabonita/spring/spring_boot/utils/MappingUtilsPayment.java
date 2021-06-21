package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.PaymentDTO;
import com.casabonita.spring.spring_boot.dto.SavePaymentDTO;
import com.casabonita.spring.spring_boot.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsPayment {

    private MappingUtilsAccount mappingUtilsAccount;

    public MappingUtilsPayment(MappingUtilsAccount mappingUtilsAccount) {
        this.mappingUtilsAccount = mappingUtilsAccount;
    }

    //из entity в dto
    public PaymentDTO mapToPaymentDTO(Payment payment){
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAccountDTO(mappingUtilsAccount.mapToAccountDTO(payment.getAccount()));
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setPurpose(payment.getPurpose());

        return dto;
    }

    //из dto в entity
    public Payment mapToPayment(PaymentDTO dto){
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setAccount(mappingUtilsAccount.mapToAccount(dto.getAccountDTO()));
        payment.setAmount(dto.getAmount());
        payment.setDate(dto.getDate());
        payment.setPurpose(dto.getPurpose());

        return payment;
    }

    //из SavePaymentDTO в entity
    public Payment mapToPayment(SavePaymentDTO savePaymentDTO){
        Payment payment = new Payment();
        payment.setAmount(savePaymentDTO.getAmount());
        payment.setDate(savePaymentDTO.getDate());
        payment.setPurpose(savePaymentDTO.getPurpose());

        return payment;
    }
}

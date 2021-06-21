package com.casabonita.spring.spring_boot.dto;

import com.casabonita.spring.spring_boot.entity.Account;

import java.util.Date;

public class PaymentDTO {

    private Integer id;
    private AccountDTO accountDTO;
    private int amount;
    private Date date;
    private String purpose;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer id, AccountDTO accountDTO, int amount, Date date, String purpose) {
        this.id = id;
        this.accountDTO = accountDTO;
        this.amount = amount;
        this.date = date;
        this.purpose = purpose;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}

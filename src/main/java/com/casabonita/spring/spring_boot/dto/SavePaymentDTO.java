package com.casabonita.spring.spring_boot.dto;

import java.util.Date;

public class SavePaymentDTO {

    private String accountNumber;
    private int amount;
    private Date date;
    private String purpose;

    public SavePaymentDTO() {
    }

    public SavePaymentDTO(String accountNumber, int amount, Date date, String purpose) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.date = date;
        this.purpose = purpose;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

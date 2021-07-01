package com.casabonita.spring.spring_boot.dto;

public class SaveAccountDTO {

    private String number;
    private String contractNumber;

    public SaveAccountDTO() {
    }

    public SaveAccountDTO(String number, String contractNumber) {
        this.number = number;
        this.contractNumber = contractNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}

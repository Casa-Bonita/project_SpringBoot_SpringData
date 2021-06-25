package com.casabonita.spring.spring_boot.dto;

public class SaveAccountDTO {

    private String number;
    private ContractDTO accountContractDTO;

    public SaveAccountDTO() {
    }

    public SaveAccountDTO(String number, ContractDTO accountContractDTO) {
        this.number = number;
        this.accountContractDTO = accountContractDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContractDTO getAccountContractDTO() {
        return accountContractDTO;
    }

    public void setAccountContractDTO(ContractDTO accountContractDTO) {
        this.accountContractDTO = accountContractDTO;
    }
}

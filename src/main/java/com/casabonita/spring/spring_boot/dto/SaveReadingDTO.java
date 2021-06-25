package com.casabonita.spring.spring_boot.dto;

import java.util.Date;

public class SaveReadingDTO {

    private String meterNumber;
    private int transferData;
    private Date transferDate;

    public SaveReadingDTO() {
    }

    public SaveReadingDTO(String meterNumber, int transferData, Date transferDate) {
        this.meterNumber = meterNumber;
        this.transferData = transferData;
        this.transferDate = transferDate;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public int getTransferData() {
        return transferData;
    }

    public void setTransferData(int transferData) {
        this.transferData = transferData;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}

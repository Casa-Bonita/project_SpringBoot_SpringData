package com.casabonita.spring.spring_boot.dto;

import com.casabonita.spring.spring_boot.entity.Meter;

import java.util.Date;

public class ReadingDTO {

    private Integer id;
    private MeterDTO meterDTO;
    private int transferData;
    private Date transferDate;

    public ReadingDTO() {
    }

    public ReadingDTO(Integer id, MeterDTO meterDTO, int transferData, Date transferDate) {
        this.id = id;
        this.meterDTO = meterDTO;
        this.transferData = transferData;
        this.transferDate = transferDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MeterDTO getMeterDTO() {
        return meterDTO;
    }

    public void setMeterDTO(MeterDTO meterDTO) {
        this.meterDTO = meterDTO;
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

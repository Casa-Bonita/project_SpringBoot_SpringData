package com.casabonita.spring.spring_boot.dto;

public class SaveMeterDTO {

    private String number;
    private PlaceDTO meterPlaceDTO;

    public SaveMeterDTO() {
    }

    public SaveMeterDTO(String number, PlaceDTO meterPlaceDTO) {
        this.number = number;
        this.meterPlaceDTO = meterPlaceDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PlaceDTO getMeterPlaceDTO() {
        return meterPlaceDTO;
    }

    public void setMeterPlaceDTO(PlaceDTO meterPlaceDTO) {
        this.meterPlaceDTO = meterPlaceDTO;
    }
}

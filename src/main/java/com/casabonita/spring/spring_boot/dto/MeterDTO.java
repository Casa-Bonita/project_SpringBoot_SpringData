package com.casabonita.spring.spring_boot.dto;

public class MeterDTO {

    private Integer id;
    private String number;
    private PlaceDTO meterPlaceDTO;

    public MeterDTO() {
    }

    public MeterDTO(Integer id, String number, PlaceDTO meterPlaceDTO) {
        this.id = id;
        this.number = number;
        this.meterPlaceDTO = meterPlaceDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

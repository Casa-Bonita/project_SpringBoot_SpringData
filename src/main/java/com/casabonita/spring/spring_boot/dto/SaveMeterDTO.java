package com.casabonita.spring.spring_boot.dto;

public class SaveMeterDTO {

    private String number;
    private int placeNumber;

    public SaveMeterDTO() {
    }

    public SaveMeterDTO(String number, int placeNumber) {
        this.number = number;
        this.placeNumber = placeNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }
}

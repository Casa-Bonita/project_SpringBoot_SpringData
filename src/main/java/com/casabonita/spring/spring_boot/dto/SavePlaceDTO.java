package com.casabonita.spring.spring_boot.dto;

public class SavePlaceDTO {

    private int number;
    private String name;
    private double square;
    private int floor;
    private String type;

    public SavePlaceDTO() {
    }

    public SavePlaceDTO(int number, String name, double square, int floor, String type) {
        this.number = number;
        this.name = name;
        this.square = square;
        this.floor = floor;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
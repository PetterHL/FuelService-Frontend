package com.Fuel.fuelservice.Objects;

public class Car {

    private Long id;
    private String RegNumber;
    private int RegYear;
    private String manufacturer;
    private String model;
    private boolean petrol;
    private User carOwner;

    public Car() {
    }

    public Car(Long id, String regNumber, int regYear, String manufacturer, String model, boolean petrol, User carOwner) {
        this.id = id;
        RegNumber = regNumber;
        RegYear = regYear;
        this.manufacturer = manufacturer;
        this.model = model;
        this.petrol = petrol;
        this.carOwner = carOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNumber() {
        return RegNumber;
    }

    public void setRegNumber(String regNumber) {
        RegNumber = regNumber;
    }

    public int getRegYear() {
        return RegYear;
    }

    public void setRegYear(int regYear) {
        RegYear = regYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isPetrol() {
        return petrol;
    }

    public void setPetrol(boolean petrol) {
        this.petrol = petrol;
    }

    public User getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(User carOwner) {
        this.carOwner = carOwner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", RegNumber='" + RegNumber + '\'' +
                ", RegYear=" + RegYear +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", petrol=" + petrol +
                ", carOwner=" + carOwner +
                '}';
    }
}

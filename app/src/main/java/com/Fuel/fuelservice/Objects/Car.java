package com.Fuel.fuelservice.Objects;

public class Car {

    private Long carId;
    private String regNumber;
    private int RegYear;
    private String manufacturer;
    private String model;
    private boolean petrol;
    private User carOwner;
    private double fuelUsage;

    public Car() {
    }

    public Car(Long carId, String regNumber, int regYear, String manufacturer, String model, boolean petrol, User carOwner, double fuelUsage) {
        this.carId = carId;
        this.regNumber = regNumber;
        this.RegYear = regYear;
        this.manufacturer = manufacturer;
        this.model = model;
        this.petrol = petrol;
        this.carOwner = carOwner;
        this.fuelUsage = fuelUsage;
    }

    public Long getId() {
        return carId;
    }

    public void setId(Long id) {
        this.carId = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        regNumber = regNumber;
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

    public double getFuelUsage() {
        return fuelUsage;
    }

    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return regNumber;
    }


    /*@Override
    public String toString() {
        return "Car{" +
                "id=" + carId +
                ", RegNumber='" + regNumber + '\'' +
                ", RegYear=" + RegYear +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", petrol=" + petrol +
                ", carOwner=" + carOwner +
                '}';
    }*/
}

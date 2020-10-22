package com.Fuel.fuelservice.Objects;

public class FuelStations {

    private Long id;
    private String name;
    private String coordinates;
    private int petrolPrice;
    private int dieselPrice;
    private boolean diesel;
    private boolean petrol;


    public FuelStations(String title) {
        this.name = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }
    public Long getID() {
        return id;
    }

    public void setStation(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "FuelStation{" +
                ", itemName='" + name + '\'' +
                ", descriptionView='" + coordinates + '\'' +
                ", PetrolPrice=" + petrolPrice +
                ", DieselPrice=" + dieselPrice +
                '}';
    }
}

package com.Fuel.fuelservice.Objects;

public class FuelStations {

    private Long id;
    private String name;
    private String coordinates;
    private int petrolPrice;
    private int dieselPrice;
    private boolean diesel;
    private boolean petrol;

    public FuelStations(Long id) {
        this.id = id;
    }

    public FuelStations(String name, String coordinates, int petrolPrice, int dieselPrice, boolean diesel, boolean petrol) {
        this.name = name;
        this.coordinates = coordinates;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.diesel = diesel;
        this.petrol = petrol;
    }

    public FuelStations(Long id, String name, String coordinates, int petrolPrice, int dieselPrice, boolean diesel, boolean petrol) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.diesel = diesel;
        this.petrol = petrol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public int getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(int petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public int getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(int dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public boolean isDiesel() {
        return diesel;
    }

    public void setDiesel(boolean diesel) {
        this.diesel = diesel;
    }

    public boolean isPetrol() {
        return petrol;
    }

    public void setPetrol(boolean petrol) {
        this.petrol = petrol;
    }

    @Override
    public String toString() {
        return "FuelStations{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", petrolPrice=" + petrolPrice +
                ", dieselPrice=" + dieselPrice +
                ", diesel=" + diesel +
                ", petrol=" + petrol +
                '}';
    }
    public String petrolToString() {
        return "Petrol = " + petrolPrice;
    }
    public String dieselToString() {
        return "Diesel = " + dieselPrice;
    }
}

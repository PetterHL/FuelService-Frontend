package com.Fuel.fuelservice.Objects;

public class FuelStations {

    private Long id;
    private String name;
    private String coordinates;
    private double petrolPrice;
    private double dieselPrice;
    private boolean diesel;
    private boolean petrol;
    private double userDistance;

    public FuelStations(Long id) {
        this.id = id;
    }

    public FuelStations(String name, String coordinates, double petrolPrice, double dieselPrice, boolean diesel, boolean petrol, double userDistance) {
        this.name = name;
        this.coordinates = coordinates;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.diesel = diesel;
        this.petrol = petrol;
        this.userDistance = userDistance;
    }

    public FuelStations(Long id, String name, String coordinates, double petrolPrice, double dieselPrice, boolean diesel, boolean petrol, double userDistance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.petrolPrice = petrolPrice;
        this.dieselPrice = dieselPrice;
        this.diesel = diesel;
        this.petrol = petrol;
        this.userDistance = userDistance;
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

    public double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(int petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public double getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(int dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public double getUserDistance() {
        return userDistance;
    }

    public void setUserDistance(double userDistance) {
        this.userDistance = userDistance;
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
                ", userDistance=" + userDistance +
                '}';
    }

    public String petrolToString() {
        return "95 | " + petrolPrice;
    }
    public String dieselToString() {
        return "D | = " + dieselPrice;
    }
}

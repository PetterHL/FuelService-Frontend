package com.Fuel.fuelservice.Objects;

public class FuelStations {

    private String title;
    private String description;
    private String price;
    private Long id;

    public FuelStations(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getID() {
        return id;
    }

    public void setName(String name) {
        this.title = name;
    }
    @Override
    public String toString() {
        return "Item{" +
                ", itemName='" + title + '\'' +
                ", descriptionView='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

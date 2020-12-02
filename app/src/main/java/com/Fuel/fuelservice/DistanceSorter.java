package com.Fuel.fuelservice;

import com.Fuel.fuelservice.Objects.FuelStations;

import java.util.Comparator;

public class DistanceSorter implements Comparator<FuelStations> {
    @Override
    public int compare(FuelStations o1, FuelStations o2) {
        return Double.compare(o2.getUserDistance(),o1.getUserDistance());
    }
}

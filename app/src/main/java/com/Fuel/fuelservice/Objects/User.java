package com.Fuel.fuelservice.Objects;

import java.util.List;

public class User {

    private String username;
    private String password;
    private String email;
    private List<String> favoriteStations;
    private String token;

    public User(String username, String password, String email, List<String> favoriteStations, String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteStations = favoriteStations;
        this.token = token;
    }

    public List<String> getFavoriteStations() {
        return favoriteStations;
    }

    public void addFavoriteStation(String name) {
        favoriteStations.add(name);
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}

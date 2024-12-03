package com.example.gamestoremanagement.model;

public class Game extends Product {
    public Game(int id, String name, String type, double price, int quantity, String category, String launchDate) {
        super(id, name, type, price, quantity, category, launchDate);
    }
}
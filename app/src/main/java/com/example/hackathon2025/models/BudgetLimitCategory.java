package com.example.hackathon2025.models;

import java.io.Serializable;

public class BudgetLimitCategory implements Serializable {
    private final String categoryName;
    private int limitAmount;
    private int amountSpent;
    private boolean isWant;

    public BudgetLimitCategory(String name, int limit, int spent, boolean isWant) {
        this.categoryName = name;
        this.limitAmount = limit;
        this.amountSpent = spent;
        this.isWant = isWant;
    }

    // Getters and setters
    public String getCategoryName() {
        return categoryName;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public boolean isWant() {
        return isWant;
    }

    public void setLimitAmount(int limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void addToAmountSpent(int amountSpent) {
        this.amountSpent += amountSpent;
    }
}

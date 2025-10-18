package com.example.hackathon2025.models;

import java.io.Serializable;

public class BudgetLimitCategory implements Serializable {
    private final String categoryName;
    private double limitAmount;
    private double amountSpent;
    private boolean isWant;

    public BudgetLimitCategory(String name, double limit, double spent, boolean isWant) {
        this.categoryName = name;
        this.limitAmount = limit;
        this.amountSpent = spent;
        this.isWant = isWant;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getAmountSpent() {
        return amountSpent;
    }
    public boolean isWant(){
        return isWant;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void addToAmountSpent(double amountSpent) {
        this.amountSpent += amountSpent;
    }
}
package com.example.hackathon2025.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Transaction implements Serializable {
    private BudgetLimitCategory category;
    private double amount;
    private LocalDate date;
    private String description;
    private boolean isIncome;

    public Transaction(BudgetLimitCategory category, double amount, LocalDate date, String description, boolean isIncome){
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.isIncome = isIncome;
    }

    public BudgetLimitCategory getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncome() {
        return isIncome;
    }

}

package com.example.hackathon2025.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Budget implements Serializable {
    List<BudgetLimitCategory> categoryList;
    List<Transaction> transactionList;
    public Budget(){
        categoryList = new ArrayList<>();
        transactionList = new ArrayList<>();
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public List<BudgetLimitCategory> getCategoryList() {
        return categoryList;
    }

    public BudgetLimitCategory getSpecificCategory(String categoryName){
        for(BudgetLimitCategory category : categoryList){
            if(category.getCategoryName().equals(categoryName)){
                return category;
            }
        }
        return null;
    }

    public void setDefaultCategories(){
        categoryList.add(new BudgetLimitCategory("Housing", 1000, 0, false));
        categoryList.add(new BudgetLimitCategory("Transportation", 500, 0, false));
        categoryList.add(new BudgetLimitCategory("Food", 200, 0, false));
        categoryList.add(new BudgetLimitCategory("Medical", 100, 0, false));
        categoryList.add(new BudgetLimitCategory("Personal Spending", 100, 0, true));
        categoryList.add(new BudgetLimitCategory("Entertainment", 200, 0, true));
    }

    public int getTotalBudget(){
        int total = 0;
        for(BudgetLimitCategory category : categoryList){
            total += (int) category.getLimitAmount();
        }
        return total;
    }

    public int getTotalSpent(){
        int total = 0;
        for(Transaction transaction : transactionList){
            total += (int) transaction.getAmount();
        }
        return total;
    }

    public void addTransaction(Transaction t){
        transactionList.add(t);
    }

    public int getWantsBudget(){
        int total = 0;
        for(BudgetLimitCategory category : categoryList){
            if(category.isWant()){
                total += (int) category.getLimitAmount();
            }
        }
        return total;
    }

    public int getWantsSpent(){
        int total = 0;
        for(Transaction transaction : transactionList){
            if(transaction.getCategory().isWant()){
                total += (int) transaction.getAmount();
            }
        }
        return total;
        }
    }


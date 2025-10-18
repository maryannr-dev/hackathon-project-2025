package com.example.hackathon2025.models;

import com.example.hackathon2025.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserItem {
    private String email;
    private String password;
    private Date dateOfBirth;

    private Budget budget;
    private List<Badges> badgesList;


    public UserItem(String lM, String p, Date dOB) {
        email = lM;
        password = p;
        dateOfBirth = dOB;
        budget = new Budget();
        budget.setDefaultCategories();
        badgesList = new ArrayList<>();
        badgesList.add(new Badges("Budget Warrior", R.drawable.budget_warrior, 0, 0));
        badgesList.add(new Badges("Literacy Scholar", R.drawable.literacy_icon,0,0));

    }

    public Budget getBudget() {
        return budget;
    }

    public List<Badges> getBadgesList(){
        return badgesList;
    }

    //getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String newLegalName) {
        this.email = newLegalName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }
}

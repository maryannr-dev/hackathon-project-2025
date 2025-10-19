package com.example.hackathon2025.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserItem implements Serializable {
    private String email;
    // Replaced password with hashedPassword and salt
    private String hashedPassword;
    private String salt;
    private Date dateOfBirth;
    private Budget budget;
    private List<Badges> badgesList;

    // Updated the constructor
    public UserItem(String e, String hashedPassword, String salt, Date dOB, Budget b, List<Badges> bL) {
        this.email = e;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.dateOfBirth = dOB;
        this.budget = b;
        this.badgesList = bL;
    }

    // Updated getters and setters for password
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // --- Other getters and setters are fine ---
    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public List<Badges> getBadgesList() {
        return badgesList;
    }

    public void setBadgesList(List<Badges> badgesList) {
        this.badgesList = badgesList;
    }
}

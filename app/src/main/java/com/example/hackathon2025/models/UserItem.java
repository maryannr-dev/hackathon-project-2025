package com.example.hackathon2025.models;

import java.util.Date;

public class UserItem {
    private String legalName;
    private String password;
    private Date dateOfBirth;

    public UserItem(String lM, String p, Date dOB) {
        legalName = lM;
        password = p;
        dateOfBirth = dOB;
    }

    //getters and setters
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String newLegalName) {
        this.legalName = newLegalName;
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

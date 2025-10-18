package com.example.hackathon2025.models;

public class UserItem {
    private String legalName;
    private String password;
    private String dateOfBirth;

    public UserItem(String lM, String p, String dOB) {
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String newDateofBirth) {
        this.dateOfBirth = newDateofBirth;
    }
}

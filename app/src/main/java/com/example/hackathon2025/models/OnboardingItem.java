package com.example.hackathon2025.models;

public class OnboardingItem {
    private final int imageRes;
    private final String title;

    public OnboardingItem(int imageRes, String title){
        this.imageRes = imageRes;
        this.title = title;
    }

    public int getImageRes() { return imageRes; }
    public String getTitle() { return title; }
}

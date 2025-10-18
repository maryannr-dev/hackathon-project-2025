package com.example.hackathon2025.models;

import android.graphics.drawable.Drawable;

public class Badges {

    private String badgeName;
    private int badgeIconId;
    private int experiencePoints;
    private int level;

    public Badges(String badgeName, int badgeIconId, int xp, int lvl) {
        this.badgeName = badgeName;
     this.badgeIconId = badgeIconId;
     experiencePoints = xp;
     level = lvl;
    }

    //Getter and setter
    public int getXp() {
        return this.experiencePoints;
    }

    public int getLvl() {
        return this.level;
    }

    public int getBadgeIconId(){
        return this.badgeIconId;
    }

    public String getBadgeName(){
        return this.badgeName;
    }

    public void addXp(int amount)  {
        this.experiencePoints = this.experiencePoints + amount; 
    }
  
    
}

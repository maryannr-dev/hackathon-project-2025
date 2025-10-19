package com.example.hackathon2025.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Badges implements Serializable {
    private int xp;
    private int lvl;
    private String badgeName;
    private int badgeIconId;

    public Badges(String badgeName, int badgeIconId, int xp, int lvl) {
     this.xp = xp;
     this.lvl = lvl;
     this.badgeName = badgeName;
     this.badgeIconId = badgeIconId;
    }

    //Getter and setter
    public int getXp() {
        return this.xp;
    }

    public int getLvl() {
        return this.lvl;
    }

    public String getBadgeName() {
        return this.badgeName;
    }

    public int getBadgeIconId() {
        return this.badgeIconId;
    }

    public void addxp(int amount)  {
        this.xp = this.xp + amount; 
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public void setBadgeIconId(int badgeIconId) {
        this.badgeIconId = badgeIconId;
    }

    public void printBadge() {
        System.out.println("Badge level: " + lvl);
        System.out.println("Badge xp: " + xp);
        System.out.println("Badge name: " + badgeName);
        System.out.println("Badge Icon ID: " + badgeIconId);
    }
    
    
}

public class Badges {
    private int experiencePoints;
    private int Level;

    public Badges(int xp, int lvl) {
     experiencePoints = xp;
     Level = lvl;
    }

    //Getter and setter
    public int getXp() {
        return this.experiencePoints;
    }

    public int getLvl() {
        return this.Level;
    }

    public void addxp(int amount)  {
        this.experiencePoints = this.experiencePoints + amount; 
    }
  
    
}

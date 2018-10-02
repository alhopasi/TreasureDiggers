package treasurediggers.domain;

import treasurediggers.domain.items.Item;


public class Player {

    private String name;
    private int x;
    private int y;
    private int visionRange;
    private String movingDirection;
    private String diggingDirection;
    private String settingUpItem;
    private int gold;
    private int health;
    private String item1;
    private String item2;
    boolean isMoving;

    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.visionRange = 10;
        this.movingDirection = "";
        this.diggingDirection = "";
        this.gold = 0;
        this.health = 100;
        this.settingUpItem = "";
        this.item1 = "Small Bomb";
        this.item2 = "Dynamite";
    }
    
    public void setSettingUpItem(String itemName) {
        this.settingUpItem = itemName;
    }
    
    public String getSettingUpItem() {
        return this.settingUpItem;
    }

    public boolean setX(int x) {
        if (x < 1 || x > 78) {
            return false;
        }
        this.x = x;
        return true;
    }
    
    public void addGold(int amount) {
        this.gold += amount;
    }
    
    public int getGold() {
        return gold;
    }
    
    public String getName() {
        return name;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVisionRange() {
        return visionRange;
    }
    
    public String getMovingDirection() {
        return movingDirection;
    }
    
    public void setMovingDirection(String direction) {
        this.movingDirection = direction;
    }
    
    public String getItem1() {
        return item1;
    }
    
    public String getItem2() {
        return item2;
    }
    
    public void setItem1(String item) {
        this.item1 = item;
    }
    
    public void setItem2(String item) {
        this.item2 = item;
    }
    
    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
    
    public String getDiggingDirection() {
        return diggingDirection;
    }
    
    public void stopActions() {
        diggingDirection = "";
        settingUpItem = "";
        movingDirection = "";
    }
    
    public void startDigging(String direction) {
        diggingDirection = direction;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    
}


package treasurediggers.domain.items;

public abstract class Bomb implements Item {
    
    private String name;
    private int activationTime;
    private int setUpTime;
    private int x;
    private int y;
    
    private int power;
    
    public Bomb(String name, int x, int y, int power, int setUpTime, int activationTime) {
        this.name = name;
        this.activationTime = activationTime;
        this.setUpTime = setUpTime;
        this.power = power;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public int getSetUpTime() {
        return this.setUpTime;
    }
    
    @Override
    public int getActivationTime() {
        return this.activationTime;
    }
    
    public int getPower() {
        return this.power;
    }
    
}

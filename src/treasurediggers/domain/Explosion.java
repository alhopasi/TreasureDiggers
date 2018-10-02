
package treasurediggers.domain;

public class Explosion {
    
    private int power;
    private int x;
    private int y;
    private String direction;
    
    public Explosion(int x, int y, int power, String direction) {
        this.power = power;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public int getPower() {
        return power;
    }
    
    @Override
    public String toString() {
        return "(" + x + ":" + y + ") Power: " + power;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void addPower(int power) {
        this.power += power;
    }
    
    public void reducePower(int power) {
        this.power -= power;
        if (this.power < 0) {
            this.power = 0;
        }
    }
    
}

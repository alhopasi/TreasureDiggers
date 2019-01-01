package treasurediggers.domain.items;

public class SandBag implements Item {

    private String name;
    private int activationTime;
    private int setUpTime;
    private int x;
    private int y;

    public SandBag(int x, int y) {

        this.name = "Sand Bag";
        this.activationTime = 10;
        this.setUpTime = 2000;
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

}

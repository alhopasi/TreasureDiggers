package treasurediggers.domain;

public class Tile {

    private TileType type;
    private int durability;

    public Tile(TileType type) {
        this.type = type;
        this.durability = type.getDurability();
    }

    public TileType getType() {
        return type;
    }
    
    public int getDurability() {
        return durability;
    }

    public void setType(TileType type) {
        this.type = type;
        this.durability = type.getDurability();
    }

    public void reduceDurability(int value) {
        if (this.type == TileType.INDESTRUCTABLE_BLOCK) {
            return;
        }
        this.durability -= value;
    }

    @Override
    public String toString() {
        return type.getName() + " - " + durability + " - " + type.getGoldValue() + "gp";
    }
}

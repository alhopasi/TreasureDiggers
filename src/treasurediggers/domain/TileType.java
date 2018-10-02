package treasurediggers.domain;

public enum TileType {

    EMPTY("empty", 1, 0),
    DOOR("door", 1, 0),
    SAND("sand", 5, 0),
    ROCK("rock", 20, 0),
    ROCKNE("rockne", 15, 0),
    ROCKNW("rocknw", 15, 0),
    ROCKSE("rockse", 15, 0),
    ROCKSW("rocksw", 15, 0),
    TREASURE_RINGSMALL("small ring", 10, 20),
    TREASURE_RINGDIAMOND("diamond ring", 100, 75),
    TREASURE_NECKLACE("necklace", 20, 30),
    TREASURE_GOBLET("goblet", 20, 50),
    INDESTRUCTABLE_BLOCK("indestructable block", -1, 0);

    private final String name;
    private final int durability;
    private final int goldValue;

    private TileType(String name, int durability, int goldValue) {
        this.name = name;
        this.durability = durability;
        this.goldValue = goldValue;
    }

    public int getDurability() {
        return this.durability;
    }
    
    public int getGoldValue() {
        return this.goldValue;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + "\nDurability: " + durability + "\nGold value: " + goldValue;
    }

}

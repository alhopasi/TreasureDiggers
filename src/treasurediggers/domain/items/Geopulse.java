package treasurediggers.domain.items;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import treasurediggers.domain.ColorEffect;
import treasurediggers.domain.GameMap;
import treasurediggers.domain.TileType;

public class Geopulse implements Item {

    private String name;
    private int activationTime;
    private int setUpTime;
    private int x;
    private int y;
    private GameMap gameMap;

    List<ColorEffect> effects;
    private int pulseRadius;
    private int maxPulseRadius;

    public Geopulse(int x, int y, List<ColorEffect> effects, GameMap gameMap) {

        this.name = "Geopulse";
        this.activationTime = 1300;
        this.setUpTime = 1000;
        this.x = x;
        this.y = y;
        this.effects = effects;
        this.gameMap = gameMap;

        pulseRadius = 0;
        maxPulseRadius = 13;
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

    public void activate() {

        Timer pulseTimer = new Timer();

        TimerTask expandPulseTask = new TimerTask() {
            @Override
            public void run() {

                ColorEffect effectOnMiss = new ColorEffect(0, 0, 0.30, 100);
                ColorEffect effectOnTreasure = new ColorEffect(0.20, 0.20, 0.50, 5000);

                if (pulseRadius >= maxPulseRadius) {
                    pulseTimer.cancel();
                    this.cancel();
                }

                pulseRadius++;

                for (int angle = 0; angle < 360; angle++) {

                    double radianMultiplier = Math.PI / 180;

                    int x1 = (int) (x + (pulseRadius * (Math.cos(angle * radianMultiplier))));
                    int y1 = (int) (y + (pulseRadius * (Math.sin(angle * radianMultiplier))));

                    if (x1 < 1 || y1 < 1 || x1 > 78 || y1 > 40) {
                        continue;
                    }
                    
                    if (effectOnMiss.hasCoordinates(x1, y1) || effectOnTreasure.hasCoordinates(x1, y1)) {
                        continue;
                    }

                    TileType tile = gameMap.getTile(x1, y1).getType();

                    if (tile == TileType.TREASURE_GOBLET || tile == TileType.TREASURE_NECKLACE
                            || tile == TileType.TREASURE_RINGDIAMOND || tile == TileType.TREASURE_RINGSMALL) {
                        effectOnTreasure.addCoordinates(x1, y1);
                    } else {
                        effectOnMiss.addCoordinates(x1, y1);
                    }
                }
                effectOnTreasure.startTimer();
                effectOnMiss.startTimer();
                effects.add(effectOnMiss);

                if (!effectOnTreasure.getCoordinates().isEmpty()) {
                    effects.add(effectOnTreasure);
                }
            }

        };
        pulseTimer.scheduleAtFixedRate(expandPulseTask, activationTime / 13, activationTime / 13);
    }

}

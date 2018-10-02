package treasurediggers.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import treasurediggers.domain.items.Dynamite;
import treasurediggers.domain.items.Geopulse;
import treasurediggers.domain.items.Item;
import treasurediggers.domain.items.SandDigger;
import treasurediggers.domain.items.SmallBomb;

public class Logic {

    private GameMap gameMap;
    private List<Player> players;
    private boolean fov;
    private int width;
    private int height;
    private int digSpeed;
    private int moveSpeed;
    private List<ExplosionToDraw> explosionsToDraw;
    private List<Item> items;
    private List<ColorEffect> colorEffects;

    public Logic(List<Player> players, int width, int height) {

        this.players = players;
        this.gameMap = new GameMap(width, height);
        this.fov = true;
        this.width = width;
        this.height = height;
        this.digSpeed = 400;
        this.moveSpeed = 500;
        this.explosionsToDraw = new ArrayList<>();
        this.items = new ArrayList<>();
        this.colorEffects = new ArrayList<>();
    }

    public GameMap getMap() {
        return this.gameMap;
    }

    public Player getPlayer1() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(0);
    }

    public Player getPlayer2() {
        if (players.size() < 2) {
            return null;
        }
        return players.get(1);
    }

    public Player getPlayer3() {
        if (players.size() < 3) {
            return null;
        }
        return players.get(2);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer4() {
        if (players.size() < 4) {
            return null;
        }
        return players.get(3);
    }

    public List<ExplosionToDraw> getExplosionsToDraw() {
        return explosionsToDraw;
    }

    public List<ColorEffect> getColorEffects() {
        return colorEffects;
    }

    public void addColorEffects(ColorEffect effect) {
        colorEffects.add(effect);
    }

    public void removeExplosionToDraw() {
        Iterator<ExplosionToDraw> it = explosionsToDraw.iterator();

        while (it.hasNext()) {
            if (it.next().getStage() == 6) {
                it.remove();
            }
        }
    }

    public List<Point> FOV() {
        List<Point> tiles = new ArrayList<>();
        double x, y;
        int i;
        for (i = 0; i < 360; i++) {
            x = Math.cos((float) i * 0.01745f);
            y = Math.sin((float) i * 0.01745f);

            DoFov(x, y, tiles);
        }
        for (int x1 = 0; x1 < width; x1++) {
            addTile(x1, 0, tiles);
            addTile(x1, 41, tiles);
        }
        for (int y1 = 0; y1 < height; y1++) {
            addTile(0, y1, tiles);
            addTile(79, y1, tiles);
        }
        return tiles;
    }

    public boolean getFov() {
        return this.fov;
    }

    public void toggleFov() {
        if (fov == true) {
            fov = false;
        } else {
            fov = true;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    private void DoFov(double x, double y, List<Point> tiles) {
        for (Player player : players) {
            int i;
            float ox, oy;
            ox = (float) player.getX() + 0.5f;
            oy = (float) player.getY() + 0.5f;

            for (i = 0; i <= player.getVisionRange(); i++) {
                addTile((int) ox, (int) oy, tiles);
                if (!isTileVisible((int) ox, (int) oy)) {
                    continue;
                }
                ox += x;
                oy += y;
            }

        }

    }

    private boolean isTileVisible(int x, int y) {
        TileType type = gameMap.getTile(x, y).getType();
        return (type == TileType.EMPTY || type == TileType.DOOR
                || type == TileType.TREASURE_GOBLET || type == TileType.TREASURE_NECKLACE
                || type == TileType.TREASURE_RINGDIAMOND || type == TileType.TREASURE_RINGSMALL);
    }

    private void addTile(int x, int y, List<Point> tiles) {
        if (!tiles.contains(new Point(x, y))) {
            tiles.add(new Point(x, y));
        }
    }

    public void move(Player player, String direction) {
        if (player == null) {
            return;
        }

        //if (player.getMovingDirection().equals(direction)) {
        //    return;
        //}
        if (!player.getDiggingDirection().equals(direction)) {
            player.stopActions();
        }

        int x = player.getX();
        int y = player.getY();

        if (direction.equals("right")) {
            x += 1;
        }
        if (direction.equals("down")) {
            y += 1;
        }
        if (direction.equals("left")) {
            x -= 1;
        }
        if (direction.equals("up")) {
            y -= 1;
        }

        if (possibleToMove(x, y)) {
            player.stopActions();
            movePlayer(player, x, y, direction);
            return;
        }

        if (possibleToDig(x, y)) {
            dig(player, direction, x, y);
            return;
        }
    }

    public void moreDigAndMoveSpeed() {
        this.moveSpeed /= 2;
        this.digSpeed /= 2;
        if (this.moveSpeed < 10) {
            this.moveSpeed = 10;
        }
        if (this.digSpeed < 10) {
            this.digSpeed = 10;
        }
    }

    private boolean possibleToMove(int x, int y) {
        TileType tile = getTileType(x, y);
        for (Player player : players) {
            if (player.getX() == x && player.getY() == y) {
                return false;
            }
        }
        if (tile == TileType.EMPTY || tile == TileType.DOOR
                || tile == TileType.TREASURE_GOBLET || tile == TileType.TREASURE_NECKLACE
                || tile == TileType.TREASURE_RINGDIAMOND || tile == TileType.TREASURE_RINGSMALL) {
            return true;
        }
        return false;
    }

    private boolean possibleToDig(int x, int y) {
        TileType tile = getTileType(x, y);
        if (tile == TileType.SAND || tile == TileType.ROCK
                || tile == TileType.ROCKNW || tile == TileType.ROCKNE
                || tile == TileType.ROCKSW || tile == TileType.ROCKSE) {
            return true;
        }
        return false;
    }

    private TileType getTileType(int x, int y) {
        return getMap().getTile(x, y).getType();
    }

    private void dig(Player player, String direction, int x, int y) {
        if (player.getDiggingDirection().equals(direction)) {
            return;
        }
        player.startDigging(direction);

        Timer diggingTimer = new Timer();
        TimerTask diggingTask = new TimerTask() {
            @Override
            public void run() {
                if (!player.getDiggingDirection().equals(direction)) {
                    diggingTimer.cancel();
                } else {
                    getMap().getTile(x, y).reduceDurability(1);
                }
                if (getMap().getTile(x, y).getDurability() == 0) {
                    getMap().getTile(x, y).setType(TileType.EMPTY);
                    player.stopActions();
                    diggingTimer.cancel();
                }
            }
        };
        diggingTimer.scheduleAtFixedRate(diggingTask, digSpeed, digSpeed);
    }

    private void movePlayer(Player player, int x, int y, String direction) {
        player.setMovingDirection(direction);

        Timer movingTimer = new Timer();
        TimerTask movingTask = new TimerTask() {
            @Override
            public void run() {
                if (!player.getMovingDirection().equals(direction)) {
                    movingTimer.cancel();
                    return;
                }
                player.setX(x);
                player.setY(y);
                //player.setMovingDirection("");

                int tileGold = getMap().getTile(x, y).getType().getGoldValue();
                if (tileGold > 0) {
                    player.addGold(tileGold);
                    getMap().getTile(x, y).setType(TileType.EMPTY);
                }
                movingTimer.cancel();

            }
        };
        movingTimer.schedule(movingTask, moveSpeed);
    }

    public void explode(int x, int y, int power) {

        ExplosionHandlerNormal explosionHandler = new ExplosionHandlerNormal(gameMap, players);
        ExplosionToDraw explosion = explosionHandler.explode(x, y, power);
        if (explosion == null) {
            return;
        }
        explosionsToDraw.add(explosion);

    }

    public void setUpItem(Player player, Item item) {

        // check if player is moving   - not working after changes
        /*if (!player.getMovingDirection().equals("")) {
            return;
        }*/
        // check if player is setting up the same item already
        if (player.getSettingUpItem().equals(item.getName()) || player.getX() != item.getX() || player.getY() != item.getY()) {
            return;
        }

        // check if there exists item on coords:
        for (Item itemToCheck : items) {
            if (itemToCheck.getX() == item.getX() && itemToCheck.getY() == item.getY()) {
                return;
            }
        }

        //then start setting up the item:
        player.stopActions();
        player.setSettingUpItem(item.getName());

        int setUpTime = item.getSetUpTime();

        Timer setUpTimer = new Timer();

        TimerTask setUpTask = new TimerTask() {
            @Override
            public void run() {
                // check if player is still setting up the item at the same coords:

                if (!player.getSettingUpItem().equals(item.getName()) || player.getX() != item.getX() || player.getY() != item.getY()) {
                    return;
                }

                // check there is no item at coord
                for (Item itemToCheck : items) {
                    if (itemToCheck.getX() == item.getX() && itemToCheck.getY() == item.getY()) {
                        return;
                    }
                }

                items.add(item);
                player.setSettingUpItem("");
                startItemActivationTime(item);
            }

        };
        setUpTimer.schedule(setUpTask, setUpTime);

    }

    public void startItemActivationTime(Item item) {
        Timer activationTimer = new Timer();

        if (item.getName().equals("Geopulse")) {
            Geopulse geopulse = (Geopulse) item;
            geopulse.activate();
        }

        TimerTask activationTask = new TimerTask() {
            @Override
            public void run() {

                if (item.getName().equals("Dynamite")) {
                    Dynamite bomb = (Dynamite) item;
                    explode(bomb.getX(), bomb.getY(), bomb.getPower());
                }
                if (item.getName().equals("Small Bomb")) {
                    SmallBomb bomb = (SmallBomb) item;
                    explode(bomb.getX(), bomb.getY(), bomb.getPower());
                }
                if (item.getName().equals("Sand Digger")) {
                    SandDigger bomb = (SandDigger) item;
                    sandDiggerExplosion(bomb.getX(), bomb.getY(), bomb.getPower(), 15);

                }
                if (item.getName().equals("Sand Bag")) {
                    getMap().getTile(item.getX(), item.getY()).setType(TileType.SAND);
                }

                items.remove(item);
            }
        };

        activationTimer.schedule(activationTask, item.getActivationTime());
    }

    private void sandDiggerExplosion(int x, int y, int power, int length) {
        explode(x, y, power);
        for (int x1 = -1; x1 >= -length; x1--) {
            if (isSandOrEmpty(x + x1, y)) {
                explode(x + x1, y, power);
            } else {
                break;
            }
        }
        for (int x1 = 1; x1 <= length; x1++) {
            if (isSandOrEmpty(x + x1, y)) {
                explode(x + x1, y, power);
            } else {
                break;
            }
        }
        for (int y1 = -1; y1 >= -length; y1--) {
            if (isSandOrEmpty(x, y + y1)) {
                explode(x, y + y1, power);
            } else {
                break;
            }
        }
        for (int y1 = 1; y1 <= length; y1++) {
            if (isSandOrEmpty(x, y + y1)) {
                explode(x, y + y1, power);
            } else {
                break;
            }
        }
    }

    private boolean isSandOrEmpty(int x, int y) {
        TileType tile = gameMap.getTile(x, y).getType();
        if (tile == TileType.SAND || tile == TileType.EMPTY || tile == TileType.DOOR
                || tile == TileType.TREASURE_GOBLET || tile == TileType.TREASURE_NECKLACE
                || tile == TileType.TREASURE_RINGDIAMOND || tile == TileType.TREASURE_RINGSMALL) {
            return true;
        }
        return false;
    }

    public String getNextItem(String item) {
        String[] itemsToChoose = {"Small Bomb", "Dynamite", "Sand Digger", "Sand Bag", "Geopulse"};
        for (int i = 0; i < itemsToChoose.length - 1; i++) {
            if (itemsToChoose[i].equals(item)) {
                return itemsToChoose[i + 1];
            }
        }
        return itemsToChoose[0];
    }

}

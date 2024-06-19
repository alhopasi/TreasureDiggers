package treasurediggers.domain;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import treasurediggers.domain.items.Dynamite;
import treasurediggers.domain.items.Geopulse;
import treasurediggers.domain.items.Item;
import treasurediggers.domain.items.SandBag;
import treasurediggers.domain.items.SandDigger;
import treasurediggers.domain.items.SmallBomb;

public class Gamescreen {

    private Logic logic;
    private Map<KeyCode, Boolean> pressedKeys;
    private Map<String, PixelReader> images;
    private Canvas canvas;
    private GraphicsContext drawingTool;

    public Gamescreen(Logic logic) {
        this.logic = logic;
        this.canvas = new Canvas(logic.getWidth()*16, (logic.getHeight() + 3)*16);
        this.drawingTool = canvas.getGraphicsContext2D();
        pressedKeys = new HashMap<>();
        images = new HashMap<>();

        loadImages();
    }

    public Scene getScene() {

        BorderPane window = new BorderPane();
        window.setCenter(canvas);

        new AnimationTimer() {
            private long before;

            @Override
            public void handle(long present) {
                if (present - before < 1_000_000_000 / 30) {
                    return;
                }
                before = present;

                drawAll();

            }
        }.start();

        Scene game = new Scene(window);

        game.setOnKeyPressed((event) -> {
        	if (logic.getGameEnded()) {
        		return;
        	}
            pressedKeys.put(event.getCode(), true);

            if (event.getCode() == logic.getKeycode("TOGGLEFOV")) {
                logic.toggleFov();
            }
            if (event.getCode() == logic.getKeycode("MORESPEED")) {
                logic.moreDigAndMoveSpeed();
            }
            if (event.getCode() == logic.getKeycode("P2ITEM1")) {
                if (logic.getMap().getTile(logic.getPlayer2().getX(), logic.getPlayer2().getY()).getType() == TileType.DOOR) {
                    logic.getPlayer2().setItem1(logic.getNextItem(logic.getPlayer2().getItem1()));
                    return;
                }
                Item item = getItem(logic.getPlayer2().getItem1(), logic.getPlayer2().getX(), logic.getPlayer2().getY());
                logic.setUpItem(logic.getPlayer2(), item);
            }
            if (event.getCode() == logic.getKeycode("P2ITEM2")) {
                if (logic.getMap().getTile(logic.getPlayer2().getX(), logic.getPlayer2().getY()).getType() == TileType.DOOR) {
                    logic.getPlayer2().setItem2(logic.getNextItem(logic.getPlayer2().getItem2()));
                    return;
                }
                Item item = getItem(logic.getPlayer2().getItem2(), logic.getPlayer2().getX(), logic.getPlayer2().getY());
                logic.setUpItem(logic.getPlayer2(), item);
            }
            if (event.getCode() == logic.getKeycode("P1ITEM1")) {
                if (logic.getMap().getTile(logic.getPlayer1().getX(), logic.getPlayer1().getY()).getType() == TileType.DOOR) {
                    logic.getPlayer1().setItem1(logic.getNextItem(logic.getPlayer1().getItem1()));
                    return;
                }
                Item item = getItem(logic.getPlayer1().getItem1(), logic.getPlayer1().getX(), logic.getPlayer1().getY());
                logic.setUpItem(logic.getPlayer1(), item);
            }
            if (event.getCode() == logic.getKeycode("P1ITEM2")) {
                if (logic.getMap().getTile(logic.getPlayer1().getX(), logic.getPlayer1().getY()).getType() == TileType.DOOR) {
                    logic.getPlayer1().setItem2(logic.getNextItem(logic.getPlayer1().getItem2()));
                    return;
                }
                Item item = getItem(logic.getPlayer1().getItem2(), logic.getPlayer1().getX(), logic.getPlayer1().getY());
                logic.setUpItem(logic.getPlayer1(), item);
            }

        });

        game.setOnMouseClicked((event) -> {
            int x = (int) event.getSceneX() / 16;
            int y = (int) (event.getSceneY() - 48) / 16;
            logic.explode(x, y, 250);
        });

        game.setOnKeyReleased((event) -> {
            pressedKeys.put(event.getCode(), false);
        });
        
        new AnimationTimer() {

            @Override
            public void handle(long nykyhetki) {
            	if (logic.getGameEnded()) {
            		return;
            	}
            	logic.checkEnd();
            }
        }.start();

        new AnimationTimer() {

            @Override
            public void handle(long nykyhetki) {
            	if (logic.getGameEnded()) {
            		return;
            	}
                if (pressedKeys.getOrDefault(logic.getKeycode("P1RIGHT"), false)) {
                    logic.move(logic.getPlayer1(), "right");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P1DOWN"), false)) {
                    logic.move(logic.getPlayer1(), "down");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P1LEFT"), false)) {
                    logic.move(logic.getPlayer1(), "left");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P1UP"), false)) {
                    logic.move(logic.getPlayer1(), "up");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P2RIGHT"), false)) {
                    logic.move(logic.getPlayer2(), "right");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P2DOWN"), false)) {
                    logic.move(logic.getPlayer2(), "down");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P2LEFT"), false)) {
                    logic.move(logic.getPlayer2(), "left");
                }
                if (pressedKeys.getOrDefault(logic.getKeycode("P2UP"), false)) {
                    logic.move(logic.getPlayer2(), "up");
                }
            }
        }.start();

        return game;
    }

    private void loadImages() {
        images.put("sand", new Image("file:sand.jpg").getPixelReader());
        images.put("door", new Image("file:door.png").getPixelReader());
        images.put("indestructable", new Image("file:indestructable.jpg").getPixelReader());
        images.put("empty", new Image("file:empty.jpg").getPixelReader());
        images.put("rock", new Image("file:rock.jpg").getPixelReader());
        images.put("players", new Image("file:players.png").getPixelReader());
        images.put("treasureRingSmall", new Image("file:treasureringsmall.png").getPixelReader());
        images.put("treasureRingDiamond", new Image("file:treasureringdiamond.png").getPixelReader());
        images.put("treasureNecklace", new Image("file:treasurenecklace.png").getPixelReader());
        images.put("treasureGoblet", new Image("file:treasuregoblet.png").getPixelReader());
        images.put("explosion", new Image("file:explosion.png").getPixelReader());
        images.put("smallbomb", new Image("file:smallbomb.png").getPixelReader());
        images.put("dynamite", new Image("file:dynamite.png").getPixelReader());
        images.put("sanddigger", new Image("file:sanddigger.png").getPixelReader());
    }

    private void drawAll() {
        WritableImage newScreen = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        PixelWriter screenWriter = newScreen.getPixelWriter();

        drawBackground(screenWriter);
        drawGameArea(screenWriter);
        drawExplosions(screenWriter);
        drawEffects(screenWriter, newScreen);

        drawingTool.drawImage(newScreen, 0, 0);

        drawPlayerInfo();
    }

    private void drawBackground(PixelWriter screenWriter) {
        for (int y = 0; y < canvas.getHeight(); y++) {
            for (int x = 0; x < canvas.getWidth(); x++) {
                screenWriter.setColor(x, y, Color.BLACK);
            }
        }
    }

    private void drawExplosions(PixelWriter screenWriter) {

        List<ExplosionToDraw> explosions = logic.getExplosionsToDraw();

        for (ExplosionToDraw explosion : explosions) {
            int stage = explosion.getStage();

            if (stage == 6) {
                continue;
            }

            PixelReader file = images.get("explosion");
            int width = 16;
            int height = 16;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = file.getArgb(x + stage * 16, y);

                    if ((pixel >> 24) == 0x00) {
                        continue;
                    }
                    Color color = file.getColor(x + stage * 16, y);

                    for (Point point : explosion.getCoordinates()) {
                        int drawX = (int) point.getX() * 16 + x;
                        int drawY = (int) point.getY() * 16 + 48 + y;
                        screenWriter.setColor(drawX, drawY, color);
                    }
                }
            }
        }
        logic.removeExplosionToDraw();
    }

    private void drawPlayerInfo() {
        for (int playerNumber = 0; playerNumber < logic.getPlayers().size(); playerNumber++) {
            Player player = logic.getPlayers().get(playerNumber);
            if (playerNumber == 0) {
                drawingTool.setFill(Color.DARKGOLDENROD);
            } else if (playerNumber == 1) {
                drawingTool.setFill(Color.CADETBLUE);
            } else if (playerNumber == 2) {
                drawingTool.setFill(Color.GREEN);
            } else if (playerNumber == 3) {
                drawingTool.setFill(Color.DIMGREY);
            }
            drawingTool.setFont(Font.font("consolas", FontWeight.BOLD, 12));
            String text = "" + player.getName() + " (" + player.getHealth() + ")" + "\nGold: " + player.getGold();
            String text2 = "Item 1: " + player.getItem1() + "\nItem 2: " + player.getItem2();
            drawingTool.fillText(text, playerNumber * 320 + 10, 20);
            drawingTool.fillText(text2, playerNumber * 320 + 110 + 10, 20);
        }
    }

    private void drawGameArea(PixelWriter screenWriter) {

        if (logic.getFov()) {
            logic.FOV().forEach(piste -> {
                int x = (int) piste.getX();
                int y = (int) piste.getY();
                drawTile(x, y, screenWriter);
            });
        } else {
            for (int y = 0; y < logic.getHeight(); y++) {
                for (int x = 0; x < logic.getWidth(); x++) {
                    drawTile(x, y, screenWriter);
                }
            }
        }
        drawItems(screenWriter);
        drawPlayers(screenWriter);
    }

    private void drawEffects(PixelWriter screenWriter, WritableImage newScreen) {

        PixelReader reader = newScreen.getPixelReader();

        for (int i = 0; i < logic.getColorEffects().size(); i++) {
            ColorEffect effect = logic.getColorEffects().get(i);
            if (effect.isDead()) {
                logic.getColorEffects().remove(i);
                i--;
                continue;
            }
            drawEffect(effect, screenWriter, newScreen);
        }
    }

    private void drawItems(PixelWriter screenWriter) {
        for (int i = 0; i < logic.getItems().size(); i++) {
            Item item = logic.getItems().get(i);

            Point checkPoint = new Point(item.getX(), item.getY());
            if (!logic.FOV().contains(checkPoint)) {
                return;
            }

            PixelReader file;

            if (item.getName().equals("Small Bomb")) {
                file = images.get("smallbomb");
            } else if (item.getName().equals("Dynamite")) {
                file = images.get("dynamite");
            } else if (item.getName().equals("Geopulse")) {
                continue;
                } else if (item.getName().equals("Sand Digger")) {
                file = images.get("sanddigger");
            } else {
                file = images.get("smallbomb");
            }

            int width = 16;
            int height = 16;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = file.getArgb(x, y);

                    if ((pixel >> 24) == 0x00) {
                        continue;
                    }
                    Color color = file.getColor(x, y);

                    int drawX = item.getX() * 16 + x;
                    int drawY = item.getY() * 16 + 48 + y;

                    screenWriter.setColor(drawX, drawY, color);
                }
            }
        }
    }

    private void drawTile(int x, int y, PixelWriter screenWriter) {

        Tile tile = logic.getMap().getTile(x, y);
        if (tile == null) {
            return;
        }

        int tilenumber = 0;

        PixelReader file;
        if (tile.getType() == TileType.SAND) {
            file = images.get("sand");
        } else if (tile.getType() == TileType.DOOR) {
            file = images.get("door");
        } else if (tile.getType() == TileType.ROCK) {
            file = images.get("rock");
        } else if (tile.getType() == TileType.ROCKNE) {
            file = images.get("rock");
            tilenumber = 1;
        } else if (tile.getType() == TileType.ROCKNW) {
            file = images.get("rock");
            tilenumber = 2;
        } else if (tile.getType() == TileType.ROCKSE) {
            file = images.get("rock");
            tilenumber = 3;
        } else if (tile.getType() == TileType.ROCKSW) {
            file = images.get("rock");
            tilenumber = 4;
        } else if (tile.getType() == TileType.INDESTRUCTABLE_BLOCK) {
            file = images.get("indestructable");
        } else if (tile.getType() == TileType.TREASURE_GOBLET) {
            file = images.get("treasureGoblet");
        } else if (tile.getType() == TileType.TREASURE_NECKLACE) {
            file = images.get("treasureNecklace");
        } else if (tile.getType() == TileType.TREASURE_RINGDIAMOND) {
            file = images.get("treasureRingDiamond");
        } else if (tile.getType() == TileType.TREASURE_RINGSMALL) {
            file = images.get("treasureRingSmall");
        } else {
            file = images.get("empty");
        }

        int width = 16;
        int height = 16;

        screenWriter.setPixels(x * 16, y * 16 + 48, width, height, file, tilenumber * 16, 0);
    }

    private void drawPlayers(PixelWriter screenWriter) {
        for (int i = 0; i < logic.getPlayers().size(); i++) {
            Player player = logic.getPlayers().get(i);
            PixelReader file;

            file = images.get("players");
            int width = 16;
            int height = 16;

            // If player is moving:
            if (!player.getMovingDirection().equals("")) {

                int animationTile = 0;
                int directionTile = 0;
                if ((System.currentTimeMillis() / 500) % 2 == 1) {
                    animationTile = 1;
                }

                if (player.getMovingDirection().equals("up")) {
                    directionTile = 2;
                }
                if (player.getMovingDirection().equals("right")) {
                    directionTile = 4;
                }
                if (player.getMovingDirection().equals("down")) {
                    directionTile = 0;
                }
                if (player.getMovingDirection().equals("left")) {
                    directionTile = 6;
                }

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = file.getArgb(x + (directionTile + animationTile) * 16, y + i * 16);

                        if ((pixel >> 24) == 0x00) {
                            continue;
                        }
                        Color color = file.getColor(x + (directionTile + animationTile) * 16, y + i * 16);

                        int drawX = player.getX() * 16 + x;
                        int drawY = player.getY() * 16 + 48 + y;

                        screenWriter.setColor(drawX, drawY, color);
                    }
                }
                continue;

            }

            // If player is setting up an item or digging:
            if (!player.getSettingUpItem().equals("") || !player.getDiggingDirection().equals("")) {

                int animationTile = 0;
                if ((System.currentTimeMillis() / 500) % 2 == 1) {
                    animationTile = 1;
                }

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = file.getArgb(x + (13 + animationTile) * 16, y + i * 16);

                        if ((pixel >> 24) == 0x00) {
                            continue;
                        }
                        Color color = file.getColor(x + (13 + animationTile) * 16, y + i * 16);

                        int drawX = player.getX() * 16 + x;
                        int drawY = player.getY() * 16 + 48 + y;

                        screenWriter.setColor(drawX, drawY, color);
                    }
                }
                continue;
            }

            // else draw standing still image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = file.getArgb(x, y + i * 16);

                    if ((pixel >> 24) == 0x00) {
                        continue;
                    }
                    Color color = file.getColor(x, y + i * 16);

                    int drawX = player.getX() * 16 + x;
                    int drawY = player.getY() * 16 + 48 + y;

                    screenWriter.setColor(drawX, drawY, color);
                }
            }
        }
    }

    private void drawEffect(ColorEffect effect, PixelWriter screenWriter, WritableImage newScreen) {

        PixelReader reader = newScreen.getPixelReader();

        for (Point point : effect.getCoordinates()) {

            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    int x1 = (int) point.getX() * 16 + x;
                    int y1 = (int) point.getY() * 16 + 48 + y;
                    Color color = reader.getColor(x1, y1);
                    double red = color.getRed() + effect.getRed();
                    double blue = color.getBlue() + effect.getBlue();
                    double green = color.getGreen() + effect.getGreen();
                    double opacity = color.getOpacity();
                    if (blue > 1) {
                        blue = 1;
                    }
                    if (red > 1) {
                        red = 1;
                    }
                    if (green > 1) {
                        green = 1;
                    }

                    screenWriter.setColor(x1, y1, new Color(red, green, blue, opacity));
                }
            }
        }
    }

    private Item getItem(String item, int x, int y) {
        if (item.equals("Small Bomb")) {
            return new SmallBomb(x, y);
        }
        if (item.equals("Dynamite")) {
            return new Dynamite(x, y);
        }
        if (item.equals("Sand Bag")) {
            return new SandBag(x, y);
        }
        if (item.equals("Geopulse")) {
            return new Geopulse(x, y, logic.getColorEffects(), logic.getMap());
        }
        if (item.equals("Sand Digger")) {
            return new SandDigger(x, y);
        }
        return null;
    }

}

package treasurediggers.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {

    private Tile[][] gameTiles;
    private int width;
    private int height;
    private int rockFormations;
    private boolean checkOverlappingRocks;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        rockFormations = 20;
        checkOverlappingRocks = false;

        createGameTiles();
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return gameTiles[x][y];
    }

    private void createGameTiles() {

        this.gameTiles = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameTiles[x][y] = new Tile(TileType.SAND);
            }
        }

        createRocks();
        createTreasuresAndBorders();
        createStartingAreas();
    }

    private void createTreasuresAndBorders() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isBorderTile(x, y)) {
                    gameTiles[x][y].setType(TileType.INDESTRUCTABLE_BLOCK);
                } else {
                    double randomNumber = new Random().nextDouble();
                    if (randomNumber < 0.003) {
                        gameTiles[x][y].setType(TileType.TREASURE_RINGDIAMOND);
                    } else if (randomNumber < 0.008) {
                        gameTiles[x][y].setType(TileType.TREASURE_GOBLET);
                    } else if (randomNumber < 0.014) {
                        gameTiles[x][y].setType(TileType.TREASURE_NECKLACE);
                    } else if (randomNumber < 0.02) {
                        gameTiles[x][y].setType(TileType.TREASURE_RINGSMALL);
                    }
                }
            }
        }
    }

    private boolean isBorderTile(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return true;
        }
        return false;
    }

    private void createStartingAreas() {
        gameTiles[1][1].setType(TileType.DOOR);
        gameTiles[2][1].setType(TileType.EMPTY);
        gameTiles[3][1].setType(TileType.EMPTY);
        gameTiles[4][1].setType(TileType.EMPTY);
        gameTiles[1][2].setType(TileType.EMPTY);
        gameTiles[1][3].setType(TileType.EMPTY);
        gameTiles[1][4].setType(TileType.EMPTY);

        gameTiles[1][height - 2].setType(TileType.DOOR);
        gameTiles[1][height - 3].setType(TileType.EMPTY);
        gameTiles[1][height - 4].setType(TileType.EMPTY);
        gameTiles[1][height - 5].setType(TileType.EMPTY);
        gameTiles[2][height - 2].setType(TileType.EMPTY);
        gameTiles[3][height - 2].setType(TileType.EMPTY);
        gameTiles[4][height - 2].setType(TileType.EMPTY);

        gameTiles[width - 2][1].setType(TileType.DOOR);
        gameTiles[width - 3][1].setType(TileType.EMPTY);
        gameTiles[width - 4][1].setType(TileType.EMPTY);
        gameTiles[width - 5][1].setType(TileType.EMPTY);
        gameTiles[width - 2][2].setType(TileType.EMPTY);
        gameTiles[width - 2][3].setType(TileType.EMPTY);
        gameTiles[width - 2][4].setType(TileType.EMPTY);

        gameTiles[width - 2][height - 2].setType(TileType.DOOR);
        gameTiles[width - 3][height - 2].setType(TileType.EMPTY);
        gameTiles[width - 4][height - 2].setType(TileType.EMPTY);
        gameTiles[width - 5][height - 2].setType(TileType.EMPTY);
        gameTiles[width - 2][height - 3].setType(TileType.EMPTY);
        gameTiles[width - 2][height - 4].setType(TileType.EMPTY);
        gameTiles[width - 2][height - 5].setType(TileType.EMPTY);
    }

    public void createRocks() {

        for (int i = 0; i < rockFormations; i++) {
            List<Point> points = createRandomRockFormation(20 + i * 5);

            int randomPlaceX = new Random().nextInt(width - 8) - 4;
            int randomPlaceY = new Random().nextInt(height - 8) - 4;

            if (checkOverlappingRocks) {
                int amountOfOverlaps = 0;
                while (true) {

                    randomPlaceX = new Random().nextInt(width - 8) - 4;
                    randomPlaceY = new Random().nextInt(height - 8) - 4;
                    if (!rockFormationOverlaps(points, randomPlaceX, randomPlaceY)) {
                        break;
                    }
                    amountOfOverlaps++;
                    if (amountOfOverlaps == 30) {
                        break;
                    }
                }

                if (amountOfOverlaps == 30) {
                    i--;
                    continue;
                }
            }

            for (Point point : points) {
                int x = (int) point.getX() + randomPlaceX;
                int y = (int) point.getY() + randomPlaceY;
                if (x < 1 || x > width - 1 || y < 1 || y > height - 1) {
                    continue;
                }
                gameTiles[x][y].setType(TileType.ROCK);
            }
        }

        //smoothRockCorners();
    }

    private boolean rockFormationOverlaps(List<Point> points, int randomX, int randomY) {
        for (Point point : points) {
            int x = (int) point.getX() + randomX;
            int y = (int) point.getY() + randomY;
            if (x < 1 || x > width - 1 || y < 1 || y > height - 1) {
                continue;
            }
            TileType tile = gameTiles[x][y].getType();
            if (tile != TileType.SAND) {
                return true;
            }
        }
        return false;
    }

    private List<Point> createRandomRockFormation(int minSize) {

        List<Point> finalPoints = new ArrayList<>();

        while (finalPoints.size() < minSize) {
            finalPoints.clear();

            Point[] points = new Point[4];

            for (int i = 0; i < 4; i++) {
                int maxWidth = new Random().nextInt(5) + 15;
                int maxHeight = new Random().nextInt(5) + 15;

                int x = new Random().nextInt(maxWidth);
                int y = new Random().nextInt(maxHeight);
                Point point = new Point(x, y);
                points[i] = point;
                finalPoints.add(point);
            }

            for (int y = 0; y <= 20; y++) {
                for (int x = 0; x <= 20; x++) {
                    Point point = new Point(x, y);
                    if (containsPoint(point, points)) {
                        finalPoints.add(point);
                    }
                }
            }
        }
        return finalPoints;
    }

    private boolean containsPoint(Point test, Point[] points) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].y > test.y) != (points[j].y > test.y)
                    && (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y - points[i].y) + points[i].x)) {
                result = !result;
            }
        }
        return result;
    }

    private void smoothRockCorners() {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TileType tile = gameTiles[x][y].getType();
                if (tile == TileType.ROCK) {
                    String direction = checkSurroundingRocks(x, y);
                    if (direction.equals("-")) {
                        continue;
                    } else if (direction.equals("ne")) {
                        gameTiles[x][y].setType(TileType.ROCKNE);
                    } else if (direction.equals("nw")) {
                        gameTiles[x][y].setType(TileType.ROCKNW);
                    } else if (direction.equals("se")) {
                        gameTiles[x][y].setType(TileType.ROCKSE);
                    } else if (direction.equals("sw")) {
                        gameTiles[x][y].setType(TileType.ROCKSW);
                    }
                }
            }
        }
    }

    private String checkSurroundingRocks(int x, int y) {
        if (gameTiles[x][y - 1].getType() == TileType.SAND && gameTiles[x + 1][y].getType() == TileType.SAND
                && gameTiles[x][y + 1].getType() == TileType.ROCK && gameTiles[x - 1][y].getType() == TileType.ROCK) {
            return "ne";
        }
        if (gameTiles[x][y - 1].getType() == TileType.ROCK && gameTiles[x + 1][y].getType() == TileType.SAND
                && gameTiles[x][y + 1].getType() == TileType.SAND && gameTiles[x - 1][y].getType() == TileType.ROCK) {
            return "se";
        }
        if (gameTiles[x][y - 1].getType() == TileType.ROCK && gameTiles[x + 1][y].getType() == TileType.ROCK
                && gameTiles[x][y + 1].getType() == TileType.SAND && gameTiles[x - 1][y].getType() == TileType.SAND) {
            return "sw";
        }
        if (gameTiles[x][y - 1].getType() == TileType.SAND && gameTiles[x + 1][y].getType() == TileType.ROCK
                && gameTiles[x][y + 1].getType() == TileType.ROCK && gameTiles[x - 1][y].getType() == TileType.SAND) {
            return "nw";
        }
        return "-";
    }
}

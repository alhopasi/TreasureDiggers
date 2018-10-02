
package treasurediggers.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExplosionHandlerNormal {
    
    private GameMap gameMap;
    private List<Player> players;
    
    public ExplosionHandlerNormal(GameMap gameMap, List<Player> players) {
        this.gameMap = gameMap;
        this.players = players;
    }
    
    public ExplosionToDraw explode(int x, int y, int power) {
        if (x < 1 || y < 1 || x > 78 || y > 40) {
            return null;
        }
        List<Explosion> explosions = new ArrayList<>();
        List<Explosion> newExplosions = new ArrayList<>();
        List<Explosion> bounces = new ArrayList<>();

        newExplosions.add(new Explosion(x, y, power, "-"));
        
        ExplosionToDraw explosionToDraw = new ExplosionToDraw(x, y);
        
        while (!newExplosions.isEmpty()) {
            explosions.clear();
            explosions.addAll(newExplosions);
            newExplosions.clear();
            addBouncesToExplosions(explosions, bounces);
            bounces.clear();
            
            mergePowerFromExplosions(explosions);

            
            for (Explosion explosion : explosions) {
                newExplosions.addAll(explosionNext(explosion.getX(), explosion.getY(), explosion.getPower(), explosion.getDirection(), bounces, explosionToDraw));
            }
        }
        return explosionToDraw;
    }
    
    private void mergePowerFromExplosions(List<Explosion> explosions) {

        for (int i = 0; i < explosions.size(); i++) {
            Explosion exp = explosions.get(i);
            if (exp.getPower() == 0) {
                explosions.remove(i);
                i--;
            }
        }

        
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            int x = explosion.getX();
            int y = explosion.getY();
            
            List<Explosion> exps = explosions.stream().filter(exp -> exp.getX() == explosion.getX() && exp.getY() == explosion.getY()).collect(Collectors.toList());
            
            if (exps.size() > 1) {
                List<Explosion> expsNorth = exps.stream().filter(exp -> exp.getDirection().equals("north")).collect(Collectors.toList());
                List<Explosion> expsEast = exps.stream().filter(exp -> exp.getDirection().equals("east")).collect(Collectors.toList());
                List<Explosion> expsSouth = exps.stream().filter(exp -> exp.getDirection().equals("south")).collect(Collectors.toList());
                List<Explosion> expsWest = exps.stream().filter(exp -> exp.getDirection().equals("west")).collect(Collectors.toList());
                
                if (expsNorth.size() > 1) {
                    int totalPowerNorth = expsNorth.stream().mapToInt(exp -> exp.getPower()).sum();
                    exps.removeAll(expsNorth);
                    exps.add(new Explosion(x, y, totalPowerNorth, "north"));
                }
                if (expsEast.size() > 1) {
                    int totalPowerEast = expsEast.stream().mapToInt(exp -> exp.getPower()).sum();
                    exps.removeAll(expsEast);
                    exps.add(new Explosion(x, y, totalPowerEast, "east"));
                }
                if (expsSouth.size() > 1) {
                    int totalPowerSouth = expsSouth.stream().mapToInt(exp -> exp.getPower()).sum();
                    exps.removeAll(expsSouth);
                    exps.add(new Explosion(x, y, totalPowerSouth, "south"));
                }
                if (expsWest.size() > 1) {
                    int totalPowerWest = expsWest.stream().mapToInt(exp -> exp.getPower()).sum();
                    exps.removeAll(expsWest);
                    exps.add(new Explosion(x, y, totalPowerWest, "west"));
                }
                
                int totalPower = exps.stream().mapToInt(exp -> exp.getPower()).sum();
                Tile tileToCheck = gameMap.getTile(x, y);
                int tileDurability = tileToCheck.getDurability();
                
                if (totalPower > tileDurability && tileToCheck.getType() != TileType.EMPTY && tileDurability != 0) {
                    int powerToRemoveFromExplosions = tileDurability / exps.size();
                    
                    tileToCheck.reduceDurability(tileDurability);
                    for (Explosion exp : exps) {
                        exp.reducePower(powerToRemoveFromExplosions);
                        if (exp.getPower() < 0) {
                            exp.addPower(Math.abs(exp.getPower()));
                        }
                    }
                }
            }
        }
    }

    private List<Explosion> explosionNext(int x, int y, int power, String direction, List<Explosion> bounces, ExplosionToDraw explosionToDraw) {
        
        List<Explosion> explosions = new ArrayList<>();

        if (power <= 1) {
            return explosions;
        }

        Tile tile = gameMap.getTile(x, y);

        if (tile.getType() == TileType.INDESTRUCTABLE_BLOCK) {
            explosionBounce(x, y, power, direction, bounces);
            return explosions;
        }
        
        checkIfPlayerIsOnTileAndRemoveHealth(x, y, power);
        
        explosionToDraw.addCoordinates(x, y);

        int tileDurability = tile.getDurability();
        if (tileDurability <= power) {
            if (tile.getType() != TileType.DOOR) {
                tile.setType(TileType.EMPTY);
            }
            power = power - tileDurability;
            if (direction.equals("north")) {
                explosions.add(new Explosion(x, y - 1, power / 2, direction));
                explosions.add(new Explosion(x + 1, y, power / 4, "east"));
                explosions.add(new Explosion(x - 1, y, power / 4, "west"));
            }
            if (direction.equals("east")) {
                explosions.add(new Explosion(x + 1, y, power / 2, direction));
                explosions.add(new Explosion(x, y - 1, power / 4, "north"));
                explosions.add(new Explosion(x, y + 1, power / 4, "south"));
            }
            if (direction.equals("south")) {
                explosions.add(new Explosion(x, y + 1, power / 2, direction));
                explosions.add(new Explosion(x + 1, y, power / 4, "east"));
                explosions.add(new Explosion(x - 1, y, power / 4, "west"));
            }
            if (direction.equals("west")) {
                explosions.add(new Explosion(x - 1, y, power / 2, direction));
                explosions.add(new Explosion(x, y - 1, power / 4, "north"));
                explosions.add(new Explosion(x, y + 1, power / 4, "south"));
            }
            if (direction.equals("-")) {
                explosions.addAll(explosionNext(x, y - 1, power / 4, "north", bounces, explosionToDraw));
                explosions.addAll(explosionNext(x, y + 1, power / 4, "south", bounces, explosionToDraw));
                explosions.addAll(explosionNext(x + 1, y, power / 4, "east", bounces, explosionToDraw));
                explosions.addAll(explosionNext(x - 1, y, power / 4, "west", bounces, explosionToDraw));
            }

        } else {
            tile.reduceDurability(power / 2);
            explosionBounce(x, y, power / 2, direction, bounces);
        }
        return explosions;
    }

    private void explosionBounce(int x, int y, int power, String direction, List<Explosion> bounces) {
        
        if (power <= 1) {
            return;
        }
        
        if (direction.equals("north ")) {
            bounces.add(new Explosion(x, y + 1, power, "south"));
        }
        if (direction.equals("east")) {
            bounces.add(new Explosion(x - 1, y, power, "west"));
        }
        if (direction.equals("south")) {
            bounces.add(new Explosion(x, y - 1, power, "north"));
        }
        if (direction.equals("west")) {
            bounces.add(new Explosion(x + 1, y, power, "east"));
        }
    }
    
    private void addBouncesToExplosions(List<Explosion> explosions, List<Explosion> bounces) {
        
        int northPower = bounces.stream().filter(exp -> exp.getDirection().equals("north")).mapToInt(exp -> exp.getPower()).sum();
        int eastPower = bounces.stream().filter(exp -> exp.getDirection().equals("east")).mapToInt(exp -> exp.getPower()).sum();
        int southPower = bounces.stream().filter(exp -> exp.getDirection().equals("south")).mapToInt(exp -> exp.getPower()).sum();
        int westPower = bounces.stream().filter(exp -> exp.getDirection().equals("west")).mapToInt(exp -> exp.getPower()).sum();
        
        int northAmount = (int) explosions.stream().filter(exp -> exp.getDirection().equals("north")).count();
        int eastAmount = (int) explosions.stream().filter(exp -> exp.getDirection().equals("east")).count();
        int southAmount = (int) explosions.stream().filter(exp -> exp.getDirection().equals("south")).count();
        int westAmount = (int) explosions.stream().filter(exp -> exp.getDirection().equals("west")).count();
        for (Explosion explosion : explosions) {
            if (explosion.getDirection().equals("north")) {
                explosion.addPower(northPower / northAmount);
            } else if (explosion.getDirection().equals("east")) {
                explosion.addPower(eastPower / eastAmount);
            } else if (explosion.getDirection().equals("south")) {
                explosion.addPower(southPower / southAmount);
            } else if (explosion.getDirection().equals("west")) {
                explosion.addPower(westPower / westAmount);
            }
        }
    }
    
    private void checkIfPlayerIsOnTileAndRemoveHealth(int x, int y, int power) {
        for (Player player : players) {
            if (player.getX() == x && player.getY() == y) {
                player.reduceHealth(power);
            }
        }
    }
    
    private void addExplosionToDraw(int x, int y, ExplosionToDraw exp) {
        
        exp.addCoordinates(x, y);
    }
    
    
    
}

package treasurediggers.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ColorEffect {

    private List<Point> coordinates;
    private double red;
    private double green;
    private double blue;
    private Timer aliveTimer;
    private TimerTask cancelTask;
    private int aliveTime;
    private boolean dead;

    public ColorEffect(double red, double green, double blue, int aliveTime) {
        this.coordinates = new ArrayList<>();
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.aliveTime = aliveTime;
        this.dead = false;

        aliveTimer = new Timer();
        cancelTask = new TimerTask() {
            @Override
            public void run() {
                dead = true;
                aliveTimer.cancel();
                this.cancel();
            }
        };
    }

    public void startTimer() {
        aliveTimer.schedule(cancelTask, aliveTime);
    }

    public List<Point> getCoordinates() {
        return coordinates;
    }
    
    public boolean hasCoordinates(int x, int y) {
        Point pointToCheck = new Point(x, y);
        if (coordinates.contains(pointToCheck)) {
            return true;
        }
        return false;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }
    
    public boolean isDead() {
        return dead;
    }

    public void addCoordinates(int x, int y) {
        Point coord = new Point(x, y);
        if (!coordinates.contains(coord)) {
            coordinates.add(new Point(x, y));
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.red) ^ (Double.doubleToLongBits(this.red) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.green) ^ (Double.doubleToLongBits(this.green) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.blue) ^ (Double.doubleToLongBits(this.blue) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColorEffect other = (ColorEffect) obj;
        if (Double.doubleToLongBits(this.red) != Double.doubleToLongBits(other.red)) {
            return false;
        }
        if (Double.doubleToLongBits(this.green) != Double.doubleToLongBits(other.green)) {
            return false;
        }
        if (Double.doubleToLongBits(this.blue) != Double.doubleToLongBits(other.blue)) {
            return false;
        }
        if (this.aliveTime != other.aliveTime) {
            return false;
        }
        if (!Objects.equals(this.coordinates, other.coordinates)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + coordinates;
    }

}


package treasurediggers.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExplosionToDraw {
    List<Point> coordinates;
    int x;
    int y;
    int stage;
    
    public ExplosionToDraw(int x, int y) {
        this.x = x;
        this.y = y;
        this.stage = 0;
        this.coordinates = new ArrayList<>();
        this.coordinates.add(new Point(x, y));
        
        Timer stageTimer = new Timer();
        TimerTask diggingTask = new TimerTask() {
            @Override
            public void run() {
                stage++;
                
                if (stage == 6) {
                    stageTimer.cancel();
                    this.cancel();
                }
            }
        };
        stageTimer.scheduleAtFixedRate(diggingTask, 100, 100);
    }
    
    public List<Point> getCoordinates() {
        return coordinates;
    }
    
    public void addCoordinates(int x, int y) {
        Point coord = new Point(x, y);
        if (!coordinates.contains(coord)) {
            coordinates.add(new Point(x, y));
        }
        
    }
    
    public int getStage() {
        return stage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.x + this.y;
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
        final ExplosionToDraw other = (ExplosionToDraw) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ") stage: " + stage;
    }
    
}

package raineduc.web2.beans;

import java.io.Serializable;

public class Result implements Serializable {
    private float xCoord;
    private float yCoord;
    private float radius;
    private boolean hit;

    public Result(float xCoord, float yCoord, float radius, boolean hit) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.radius = radius;
        this.hit = hit;
    }

    public float getXCoord() {
        return xCoord;
    }

    public float getYCoord() {
        return yCoord;
    }

    public float getRadius() {
        return radius;
    }

    public boolean didHit() {
        return hit;
    }
}

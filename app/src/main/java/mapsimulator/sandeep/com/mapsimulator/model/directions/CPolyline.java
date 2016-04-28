package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;

public class CPolyline implements Serializable {
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}

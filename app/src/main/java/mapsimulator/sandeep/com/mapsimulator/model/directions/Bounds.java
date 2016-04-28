package mapsimulator.sandeep.com.mapsimulator.model.directions;


import java.io.Serializable;


public class Bounds implements Serializable {
    private Bound northeast;
    private Bound southwest;

    public Bound getNortheast() {
        return northeast;
    }

    public void setNortheast(Bound northeast) {
        this.northeast = northeast;
    }

    public Bound getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Bound southwest) {
        this.southwest = southwest;
    }
}

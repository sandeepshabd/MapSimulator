package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;
import java.util.ArrayList;


public class JsonRootDirections implements Serializable {
    private ArrayList<Route> routes;
    private String status;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

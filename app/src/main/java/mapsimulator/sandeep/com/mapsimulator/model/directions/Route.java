package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;
import java.util.ArrayList;


public class Route implements Serializable {
    private Bounds bounds;
    private String copyrights;
    private ArrayList<Leg> legs;
    private CPolyline overview_polyline;
    private String summary;
    private ArrayList<String> warnings;
    private ArrayList<Integer> waypoint_order;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public CPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(CPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<String> warnings) {
        this.warnings = warnings;
    }

    public ArrayList<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(ArrayList<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}

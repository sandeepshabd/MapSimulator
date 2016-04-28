package mapsimulator.sandeep.com.mapsimulator.model.directions;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Step implements Serializable {
    private Data distance;
    private Data duration;
    private Bound end_location;
    private String html_instructions;
    private CPolyline polyline;
    private Bound start_location;
    private String travel_mode;
    private String maneuver;
    private ArrayList<LatLng> geoPath;

    public Data getDistance() {
        return distance;
    }

    public void setDistance(Data distance) {
        this.distance = distance;
    }

    public Data getDuration() {
        return duration;
    }

    public void setDuration(Data duration) {
        this.duration = duration;
    }

    public Bound getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Bound end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public CPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(CPolyline polyline) {
        this.polyline = polyline;
    }

    public Bound getStart_location() {
        return start_location;
    }

    public void setStart_location(Bound start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getManeuver() {
        return this.maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public ArrayList<LatLng> getGeoPath() {
        return geoPath;
    }

    public void setGeoPath(ArrayList<LatLng> geoPath) {
        this.geoPath = geoPath;
    }
}

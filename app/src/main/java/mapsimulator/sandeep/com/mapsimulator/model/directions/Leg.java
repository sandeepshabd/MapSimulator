package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;
import java.util.ArrayList;


public class Leg implements Serializable {
    private Data distance;
    private Data duration;
    private String end_address;
    private Bound end_location;
    private String start_address;
    private Bound start_location;
    private ArrayList<Step> steps;
   // private ArrayList<String> via_waypoint;
    private boolean is_hidden = false;

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

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Bound getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Bound end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public Bound getStart_location() {
        return start_location;
    }

    public void setStart_location(Bound start_location) {
        this.start_location = start_location;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

//    public ArrayList<String> getVia_waypoint() {
//        return via_waypoint;
//    }
//
//    public void setVia_waypoint(ArrayList<String> via_waypoint) {
//        this.via_waypoint = via_waypoint;
//    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }
}

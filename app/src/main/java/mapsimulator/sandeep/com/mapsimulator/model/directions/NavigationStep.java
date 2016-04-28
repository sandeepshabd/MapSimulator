package mapsimulator.sandeep.com.mapsimulator.model.directions;

import java.io.Serializable;

public class NavigationStep implements Serializable {
	
	private String maneuver;
	private String direction;
	private String distance;
	
	public NavigationStep(String maneuver, String direction, String distance) {
		super();
		this.maneuver = maneuver;
		this.direction = direction;
		this.distance = distance;
	}
	
	public String getManeuver() {
		return maneuver;
	}

	public void setManeuver(String maneuver) {
		this.maneuver = maneuver;
	}

	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getDistance() {
		return distance;
	}
	
	public void setDistance(String distance) {
		this.distance = distance;
	}

}

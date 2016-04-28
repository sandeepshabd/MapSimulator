package mapsimulator.sandeep.com.mapsimulator.model.directions;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class Bound implements Serializable {
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public LatLng convertToLatLng() {
        return new LatLng(this.lat, this.lng);
    }
}

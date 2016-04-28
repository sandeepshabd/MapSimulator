package mapsimulator.sandeep.com.mapsimulator.model.marker;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by sandeepshabd on 4/27/16.
 */
public class RouteMarker {
    public static final int MARKER_START = 1;
    public static final int MARKER_WAYPOINT = 2;
    public static final int MARKER_END = 3;
    public Marker mMarker;
    public int type;

    public RouteMarker(int type, Marker marker) {
        this.type = type;
        this.mMarker = marker;
    }
}

package mapsimulator.sandeep.com.mapsimulator.helper;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sandeepshabd on 4/28/16.
 */
public class NavigationHelper {

    public static float getBearing(LatLng start, LatLng end) {
        float bearing = 0.0f;
        if (null != start && null != end) {
            Location sLoc = new Location("Start");
            sLoc.setLongitude(start.longitude);
            sLoc.setLatitude(start.latitude);

            Location eLoc = new Location("End");
            eLoc.setLongitude(end.longitude);
            eLoc.setLatitude(end.latitude);

            bearing = sLoc.bearingTo(eLoc);
        }
        return bearing;
    }

}

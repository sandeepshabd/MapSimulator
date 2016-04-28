package mapsimulator.sandeep.com.mapsimulator.helper;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sandeepshabd on 4/23/16.
 */

public class LocationHelper {
    Context context;
    LocationManager locationManager;
    //LOCHBRIDGE - 42.329260, -83.046386
    //42.332774, -83.046006
    public static double curentLatitude=42.332774;
    public static double currentLongitude= -83.046006;
    public static LatLng currentLatLng;

    //DTW airport
    //42.216605, -83.355404
    public static double destinationLatitude=42.216605;
    public static double destinationLongitude= -83.355404;

    public static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    public static final LatLng LOCHBRIDGE = new LatLng(42.332774, -83.046006);
    public static final LatLng DTW = new LatLng(42.216605, -83.355404);


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            curentLatitude = location.getLatitude();
            currentLongitude =location.getLongitude();
            currentLatLng = new LatLng(curentLatitude,currentLongitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public LocationHelper(Context ctx){
        context=ctx;
        locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
    }



}

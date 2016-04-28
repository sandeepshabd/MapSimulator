package mapsimulator.sandeep.com.mapsimulator;

import android.app.Application;

import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;

/**
 * Created by sandeepshabd on 4/23/16.
 */
public class MapApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        LocationHelper locationHelper = new LocationHelper(this);
    }
}

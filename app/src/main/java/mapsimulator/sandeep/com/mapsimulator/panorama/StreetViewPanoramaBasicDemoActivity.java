package mapsimulator.sandeep.com.mapsimulator.panorama;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import mapsimulator.sandeep.com.mapsimulator.R;
import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;

/**
 * Created by sandeepshabd on 4/23/16.
 */
public class StreetViewPanoramaBasicDemoActivity extends AppCompatActivity {
    private static final int PAN_BY = 30;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama_1);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetViewPanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            panorama.setPosition(LocationHelper.LOCHBRIDGE);
                            //panorama.setPanningGesturesEnabled(false);
                            //mSvp.setUserNavigationEnabled(false);


                            StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                                    .zoom(panorama.getPanoramaCamera().zoom)
                                    .tilt(panorama.getPanoramaCamera().tilt)
                                    .bearing(panorama.getPanoramaCamera().bearing - PAN_BY)
                                    .build();
                        }
                    }
                });
    }
}

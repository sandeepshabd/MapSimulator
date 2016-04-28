package mapsimulator.sandeep.com.mapsimulator.activity;

import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import mapsimulator.sandeep.com.mapsimulator.R;
import mapsimulator.sandeep.com.mapsimulator.helper.DirectionHelper;
import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Leg;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Route;
import mapsimulator.sandeep.com.mapsimulator.presenter.MapsPresenter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float ZOOM_LEVEL = 14.0f;
    private static final float TILT_LEVEL = 0.0f;


    private MapsPresenter mapsPresenter;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapsPresenter = new MapsPresenter();
        mapsPresenter.activty=this;

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @DebugLog
    @Override
    protected void onDestroy() {
        mapsPresenter.unsubscribe();
        super.onDestroy();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng currentlocation=LocationHelper.LOCHBRIDGE;

//        // Add a marker in Sydney and move the camera
//       // LatLng currentlocation = new LatLng(LocationHelper.curentLatitude, LocationHelper.currentLongitude);
//        mMap.addMarker(new MarkerOptions().position(currentlocation).title("Start Location."));
//        mMap.addMarker(new MarkerOptions().position(LocationHelper.DTW).title("Destination."));
//
//        CircleOptions circleOptions = new CircleOptions()
//                .fillColor(Color.BLUE)
//                .center(currentlocation)
//                .radius(1); // In meters
//        Circle circle = mMap.addCircle(circleOptions);
//
////        CameraUpdate center=
////                CameraUpdateFactory.newLatLng(currentlocation);
////        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(currentlocation)      // Sets the center of the map to Mountain View
//                .zoom(ZOOM_LEVEL)                   // Sets the zoom
//                //.bearing(180)                // Sets the orientation of the camera to east
//                .tilt(TILT_LEVEL)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


// Get back the mutable Circle

       // mMap.moveCamera(center);
       // mMap.animateCamera(zoom);

        mapsPresenter.getDirections();
    }

    @DebugLog
    public Marker showMarkerLocation(LatLng location,int displayResourceID,String snippetAddress, String titleAddress){
        //showMapStartLocation(leg.getStart_location().convertToLatLng(),R.drawable.icn_map_start,leg.getStart_address(),leg.getStart_address())

        return mMap.addMarker(new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromResource(displayResourceID))
                        .draggable(false)
                        .anchor(0.0f, 1.0f)
                        .snippet(snippetAddress)
                        .title(titleAddress)
        );

    }

    @DebugLog
    public Polyline addPolyLineToMap(ArrayList<LatLng> location){
        return mMap.addPolyline(new PolylineOptions()
                .addAll(location)
                .width(DirectionHelper.ROUTE_WIDTH)
                .color(Color.parseColor(DirectionHelper.ROUTE_COLOR))
                .geodesic(true));
    }
    @DebugLog
    public void animateMapCamera(LatLngBounds bounds){
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}

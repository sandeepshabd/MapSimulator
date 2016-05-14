package mapsimulator.sandeep.com.mapsimulator.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import hugo.weaving.DebugLog;
import mapsimulator.sandeep.com.mapsimulator.R;
import mapsimulator.sandeep.com.mapsimulator.activity.MapsActivity;
import mapsimulator.sandeep.com.mapsimulator.backend.RxGoogleMapRetrofit;
import mapsimulator.sandeep.com.mapsimulator.helper.DirectionHelper;
import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;
import mapsimulator.sandeep.com.mapsimulator.helper.NavigationHelper;
import mapsimulator.sandeep.com.mapsimulator.listner.MarkerNavigationListner;
import mapsimulator.sandeep.com.mapsimulator.model.directions.JsonRootDirections;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Leg;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Route;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Step;
import mapsimulator.sandeep.com.mapsimulator.model.marker.RouteMarker;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by sandeepshabd on 4/27/16.
 */
public class MapsPresenter {
    private final static String TAG = "MapsPresenter";

    private Subscription subscription;
    private JsonRootDirections jsonRootDirections;
    private  ArrayList<RouteMarker> mRouteMarkerList;
    private boolean moreLegsAfterThis = false;
    private Polyline mRoutePolyLine = null;

    private View mProgressBarContainer = null;

    private ArrayList<Leg> currentRouteLegs;
    private Leg mCurrentLeg;
    int mCurrentLegIndex = 0;
    LatLng mCurrentLocation;
    private Marker navigationMarker;
    private int mMapAnimation = -1;
    private CameraPosition.Builder mCameraPositionBuilder;

    private static final float ZOOM_LEVEL = 16.0f;
    private static final float TILT_LEVEL = 0.0f;
    private float bearing = 0.0f;

    private ArrayList<LatLng> totalNavPath;
    private int totalNavPointsCount=0;

    public MapsActivity activty;
    private  Handler handler = null;




    private GoogleMap.CancelableCallback mCancelableCallbackStartNavigate =
            new GoogleMap.CancelableCallback() {

                @DebugLog
                @Override
                public void onFinish() {
                    mMapAnimation = 1;
                    updatePosition();

                }

                @DebugLog
                @Override
                public void onCancel() {
                    mMapAnimation = 1;
                }
            };

    private GoogleMap.CancelableCallback mCancelableCallbackMap =
            new GoogleMap.CancelableCallback() {

                @DebugLog
                @Override
                public void onFinish() {
                    mMapAnimation = 1;
                    initializeNavigation();


                }

                @DebugLog
                @Override
                public void onCancel() {
                    mMapAnimation = 1;
                    initializeNavigation();
                }
            };


    @DebugLog
    public void getDirections(){
        this.subscription = RxGoogleMapRetrofit.makeCall().subscribe(new Subscriber<JsonRootDirections>()
        {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("MapsActivity", e.getMessage(), e);
                if (e instanceof HttpException) {
                    HttpException response = (HttpException) e;
                    int code = response.code();
                    Log.e("MapsActivity", "retrofitError code:" + code);
                }
            }

            @Override
            public void onNext(JsonRootDirections mapDirection) {
                jsonRootDirections=mapDirection;
                initializeMapView();
                Gson gson = new Gson();
                Log.d("MapsActivity", gson.toJson(jsonRootDirections));
            }
        });

    }

    @DebugLog
    public void initializeNavigation(){

        try {
            Thread.sleep(10);
        }catch(Exception e){

        }
        mCurrentLegIndex=0;
        bearing = 0.0f;
        currentRouteLegs = jsonRootDirections.getRoutes().get(0).getLegs();
        Log.i(TAG,"size of legs:"+currentRouteLegs.size());
        startNavigation(-1,bearing);

    }

    @DebugLog
    public void startNavigation(int legIndex,float bearing){
        activty.toggleMapGestures(false);
        CameraPosition.Builder mPositionBuilder = new CameraPosition.Builder()
                .tilt(TILT_LEVEL)
                .zoom(ZOOM_LEVEL)
                .target(LocationHelper.LOCHBRIDGE);

        activty.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mPositionBuilder.build()),10,mCancelableCallbackStartNavigate);

        totalNavPath = new ArrayList<LatLng>();
        for(Leg leg : currentRouteLegs) {

            ArrayList<Step> steps = leg.getSteps();
            for (Step step : steps.subList(1, steps.size())) {
                ArrayList<LatLng> latLngPoints = DirectionHelper.getPointsAtDistance(step.getPolyline().getPoints(), 0.5);
                for (LatLng latLngPoint : latLngPoints) {
                    totalNavPath.add(latLngPoint);
                }
            }
        }
    }




    @DebugLog
    private void updatePosition(){
        mCurrentLocation=totalNavPath.get(0);
        bearing =0.0f;
         if(navigationMarker==null){
            Log.i(TAG,"get new marker");
            navigationMarker= activty.getNavigationMarker( mCurrentLocation,bearing);
        }else{
            Log.i(TAG,"set new liocation for marker");
            navigationMarker.setPosition(mCurrentLocation);
            if(bearing!=0.0){
                navigationMarker.setRotation(bearing);
            }
        }

        if (null == mCameraPositionBuilder) {
            Log.i(TAG,"create new camera");
            mCameraPositionBuilder = CameraPosition.builder();
            mCameraPositionBuilder.tilt(TILT_LEVEL);
            mCameraPositionBuilder.zoom(ZOOM_LEVEL);
      }
        mCameraPositionBuilder.target(mCurrentLocation);
        totalNavPointsCount=0;
        handler = new Handler();
        final int delay =200;
        handler.postDelayed(new Runnable() {
            @DebugLog
            @Override
            public void run() {

                if(totalNavPointsCount<totalNavPath.size()){

                    LatLng newPosition = totalNavPath.get(totalNavPointsCount);
                    float newBearing = NavigationHelper.getBearing(mCurrentLocation,newPosition);
                    mCurrentLocation=newPosition;
                    Log.i(TAG,"set new liocation for marker:"+newPosition.latitude+" Longitude:"+newPosition.longitude);
                    navigationMarker.setPosition(newPosition);
                    navigationMarker.setRotation(newBearing);
                    mCameraPositionBuilder.target(newPosition);
                    activty.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPositionBuilder.build()));

                    totalNavPointsCount+=5;

                    handler.postDelayed(this, delay);
                }else{
                    Log.i(TAG,"finished the drive");
                }
             }
        }, delay);


    }

    @DebugLog
    public void initializeMapView(){
        mRouteMarkerList = new ArrayList<RouteMarker>();
        moreLegsAfterThis = false;
        if (null != mRoutePolyLine) {
            mRoutePolyLine.remove();
            mRoutePolyLine = null;
        }
        showMapDirections();


    }

    @DebugLog
    private void showMapDirections(){

        Route route = jsonRootDirections.getRoutes().get(0);
        ArrayList<Leg> legs = route.getLegs();
        int legIndex=0;
        for (Leg leg:legs) {
            legs.set(legIndex, DirectionHelper.getGeopath(leg));
            if (legIndex == 0) {
                // start marker is only added for the starting point, not for way points

                Marker startMarker = activty.showMarkerLocation(leg.getStart_location().convertToLatLng(),
                        R.drawable.icn_map_start, leg.getStart_address(), leg.getStart_address());
                mRouteMarkerList.add(new RouteMarker(RouteMarker.MARKER_START, startMarker));
            }
            legIndex++;
            if(legIndex<legs.size()-1){
                moreLegsAfterThis = true;
            }else{
                moreLegsAfterThis = false;
            }
            int displayResourceID = moreLegsAfterThis ? (leg.isIs_hidden() ? R.drawable.blue_flag_pin_small : R.drawable.icn_map_waypoint) : R.drawable.icn_map_destination;
            Marker endMarker = activty.showMarkerLocation(leg.getEnd_location().convertToLatLng(),
                    displayResourceID, leg.getEnd_address(), leg.getEnd_address());
            mRouteMarkerList.add(new RouteMarker(moreLegsAfterThis ? RouteMarker.MARKER_WAYPOINT : RouteMarker.MARKER_END, endMarker));

       }
        mRoutePolyLine = activty.addPolyLineToMap(DirectionHelper.getOverviewPolyLine(route.getOverview_polyline().getPoints()));
        LatLngBounds bounds = new LatLngBounds(route.getBounds().getSouthwest().convertToLatLng(), route.getBounds().getNortheast().convertToLatLng());
        activty.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100), 10, mCancelableCallbackMap);

    }
    @DebugLog
    public void unsubscribe(){
        if(this.subscription!=null){
            this.subscription.unsubscribe();
        }

    }


}

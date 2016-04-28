package mapsimulator.sandeep.com.mapsimulator.presenter;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.Gson;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import mapsimulator.sandeep.com.mapsimulator.R;
import mapsimulator.sandeep.com.mapsimulator.activity.MapsActivity;
import mapsimulator.sandeep.com.mapsimulator.backend.RxGoogleMapRetrofit;
import mapsimulator.sandeep.com.mapsimulator.helper.DirectionHelper;
import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;
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

    private Subscription subscription;
    private JsonRootDirections jsonRootDirections;
    private  ArrayList<RouteMarker> mRouteMarkerList;
    private boolean moreLegsAfterThis = false;
    private Polyline mRoutePolyLine = null;


    public MapsActivity activty;
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
                initialize();
                Gson gson = new Gson();
                Log.d("MapsActivity", gson.toJson(jsonRootDirections));
            }
        });

    }
    @DebugLog
    public void initialize(){
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
        activty.animateMapCamera(bounds);

    }
    @DebugLog
    public void unsubscribe(){
        if(this.subscription!=null){
            this.subscription.unsubscribe();
        }

    }


}

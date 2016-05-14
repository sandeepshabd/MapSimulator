package mapsimulator.sandeep.com.mapsimulator.helper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Leg;
import mapsimulator.sandeep.com.mapsimulator.model.directions.Step;

/**
 * Created by sandeepshabd on 4/27/16.
 */
public class DirectionHelper {

    public static final int ROUTE_WIDTH = 8;
    public static final String ROUTE_COLOR = "#0099CC";

    @DebugLog
    public static Leg getGeopath(Leg leg) {

        Step firstStep = new Step();
        firstStep.setGeoPath(new ArrayList<LatLng>());
        firstStep.getGeoPath().add(new LatLng(leg.getStart_location().getLat(), leg.getStart_location().getLng()));

        ArrayList<Step> steps= leg.getSteps();
        steps.add(0, firstStep);

        // add steps
        for (Step step:steps.subList(1,steps.size())) {
            step.setGeoPath(getPointsAtDistance(step.getPolyline().getPoints(), 0.5));
        }
        return leg;

    }
    @DebugLog

    public static ArrayList<LatLng> getPointsAtDistance(String poly, double meters) {
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        ArrayList<LatLng> polyLine = decodePoly(poly);
        double dist = 0, oldDist = 0, next = meters;
        for (int i = 1; i < polyLine.size(); i++) {
            oldDist = dist;
            dist += getDistanceFromLatLonInKm(polyLine.get(i), polyLine.get(i - 1));
            while (dist > next) {
                LatLng p1 = polyLine.get(i - 1);
                LatLng p2 = polyLine.get(i);
                double m = (next - oldDist) / (dist - oldDist);
                points.add(new LatLng(p1.latitude + (p2.latitude - p1.latitude) * m, p1.longitude + (p2.longitude - p1.longitude) * m));
                next += meters;
            }
        }
        return points;
    }

    @DebugLog
    static private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }

    @DebugLog
    static private  double getDistanceFromLatLonInKm(LatLng start, LatLng end) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(end.latitude - start.latitude);  // deg2rad below
        double dLon = deg2rad(end.longitude - start.longitude);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(start.latitude)) * Math.cos(deg2rad(end.latitude)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Distance in km
        return distance * 1000;
    }

    static private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    @DebugLog
    public static  ArrayList<LatLng> getOverviewPolyLine(String points) {
        ArrayList<LatLng> geoPath = new ArrayList<LatLng>();
        if (null != points) {
             geoPath.addAll(decodePoly(points));
        }
        return geoPath;
    }
}

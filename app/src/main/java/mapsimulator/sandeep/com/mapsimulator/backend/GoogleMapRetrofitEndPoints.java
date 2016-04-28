package mapsimulator.sandeep.com.mapsimulator.backend;

import mapsimulator.sandeep.com.mapsimulator.model.directions.JsonRootDirections;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sandeepshabd on 4/26/16.
 */
public interface GoogleMapRetrofitEndPoints {

    @GET("/maps/api/directions/json?sensor=false&mode=driving&alternatives=true")
    Observable<JsonRootDirections> getRoutes(@Query(value="origin") String origin, @Query(value="destination") String dstLat);
}

package mapsimulator.sandeep.com.mapsimulator.backend;

import mapsimulator.sandeep.com.mapsimulator.helper.LocationHelper;

import mapsimulator.sandeep.com.mapsimulator.model.directions.JsonRootDirections;

import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sandeepshabd on 4/26/16.
 */
public class RxGoogleMapRetrofit {

    public static final String BASE_URL = "http:///maps.googleapis.com/";
    public static Observable<JsonRootDirections>   makeCall(){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit=new RetrofitBuilder().getRetrofit(BASE_URL, rxAdapter);

        GoogleMapRetrofitEndPoints retrofitEndPoints = retrofit.create(GoogleMapRetrofitEndPoints.class);
        String origin = LocationHelper.curentLatitude+","+LocationHelper.currentLongitude;
        String destination = LocationHelper.destinationLatitude+","+LocationHelper.destinationLongitude;
        Observable<JsonRootDirections> call = retrofitEndPoints.getRoutes(origin, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return call;

    }
}

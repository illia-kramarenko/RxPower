package com.example.illia.rxpower.api;

import com.example.illia.rxpower.model.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by illia on 13.02.16.
 */
public interface WeatherService {

    @GET("/data/2.5/weather")
    Observable<WeatherModel> getWeather(@Query("q") String city, @Query("appid") String appId);
}

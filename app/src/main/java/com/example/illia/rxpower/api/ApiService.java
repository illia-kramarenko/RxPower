package com.example.illia.rxpower.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by illia on 13.02.16.
 */
public class ApiService {

    public static final String BASE_URL = "http://api.openweathermap.org";
    public static final String APP_ID = "8b700b2be37da1f9873fd8010017954a";

    private static WeatherService mService;

    public static WeatherService getService() {
        if (mService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mService = retrofit.create(WeatherService.class);
        }
        return mService;

    }

}

package com.bsuleymen.rxjava2test;

import com.bsuleymen.rxjava2test.Model.CitiesResponse;
import com.bsuleymen.rxjava2test.Model.WeatherForCityResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("maps/api/place/autocomplete/json")
    Observable<CitiesResponse> listCities(@Query("input") String input,
                                          @Query("components") String country,
                                          @Query("types") String type,
                                          @Query("key") String key);

    @GET("data/2.5/weather")
    Observable<WeatherForCityResponse> getWeather(@Query("q") String city,
                                                  @Query("lang") String lang,
                                                  @Query("units") String metric,
                                                  @Query("appid") String key);
}

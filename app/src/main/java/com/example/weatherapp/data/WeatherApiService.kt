package com.example.weatherapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String = "Bishkek",
        @Query("appid") appid: String = "4f2ee7893d87fd4a1eed5455b1b75f2d",
        @Query("units") units: String = "metric"
    ): Call<WeatherModel>
}
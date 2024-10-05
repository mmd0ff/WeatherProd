package com.example.weatherprod

import com.example.weatherprod.Model.WeatherDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WService {
    @GET("v1/current.json")

    suspend fun getCurrentWeatherByCity(
        @Query("q") location: String
    ): Response<WeatherDTO>

    @GET("v1/forecast.json")

    suspend fun getDailyWeather(
        @Query("q") location: String,
        @Query("days") days: Int = 4
    ): Response<WeatherDTO>


}
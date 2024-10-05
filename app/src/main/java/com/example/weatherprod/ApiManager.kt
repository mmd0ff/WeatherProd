//package com.example.weatherprod
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//object ApiManager {
//    private var okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(WeatherApiInterceptorKey())
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        })
//        .build()
//
//
//    private var client: Retrofit = Retrofit.Builder()
//        .baseUrl("https://api.weatherapi.com/")
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//
//    fun getWService(): WService {
//        return client.create(WService::class.java)
//
//    }
//

//}
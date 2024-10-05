package com.example.weatherprod

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class WeatherApiInterceptorKey @Inject constructor() : Interceptor {
    private val key = "45a06db7ac18420fa75154429242309"


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.d("WeatherApiInterceptorKey", "Интерцептор вызывается")
        val originalUrl = originalRequest.url
        Log.d("WeatherApiInterceptorKey", "Original URL: ${originalUrl}")
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("key", key)
            .build()
        Log.d("WeatherApiInterceptorKey", "New URL: ${newUrl}")
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
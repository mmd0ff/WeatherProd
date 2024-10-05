package com.example.weatherprod

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object ApiManagerWithModule {

    @Provides
    fun  provideHttpLoggingInterceptor() :HttpLoggingInterceptor{
        val level =  HttpLoggingInterceptor.Level.BODY
        val interceptor =HttpLoggingInterceptor()
        interceptor.level = level
        return interceptor

    }
    @Provides
    fun provideOkhttpClient(
        weaterApiInterceptorKey: WeatherApiInterceptorKey,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(weaterApiInterceptorKey)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }
    @Provides
    fun provideGsonConverterFactory() :GsonConverterFactory{
        return GsonConverterFactory.create()

    }
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    }
    @Provides
    fun provideWService(retrofit: Retrofit):WService{
        return retrofit.create(WService::class.java)
    }

}
package com.example.restaurants.API

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    private val BASE_URL: String = "https://api.yelp.com"
    private var API_KEY: String = com.example.restaurants.BuildConfig.API_KEY


    fun getClient(): Retrofit {
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            var newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()

            chain.proceed(newRequest)
        }.build()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}
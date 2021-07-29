package com.example.restaurants.API

import com.example.restaurants.APIBusinessId.RestaurantsId
import com.example.restaurants.APIBusinessSearch.Restaurants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("/v3/businesses/search?")
    fun getRestaurants(@Query("location") Location: String): Observable<Restaurants>

    @GET("/v3/businesses/{id}")
    fun getRestaurantsId(@Path("id") Id: String): Observable<RestaurantsId>


}
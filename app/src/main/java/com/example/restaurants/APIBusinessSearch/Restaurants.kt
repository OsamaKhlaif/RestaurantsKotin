package com.example.restaurants.APIBusinessSearch

data class Restaurants(
    val businesses: List<Businesse>,
    val region: Region,
    val total: Int
)
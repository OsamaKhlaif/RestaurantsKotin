package com.example.restaurants

class Items(titleText: String, restaurantsTypeAttributes: List<RestaurantsSearchAttributes>) {

    private var titleText: String = titleText;
    private var restaurantsTypeAttributes: List<RestaurantsSearchAttributes> =
        restaurantsTypeAttributes

    fun getTitleText(): String {
        return titleText
    }

    fun setTitleText(titleText: String) {
        this.titleText = titleText
    }

    fun getRestaurantsTypeAttributes(): List<RestaurantsSearchAttributes> {
        return restaurantsTypeAttributes
    }

    fun setRestaurantsTypeAttributes(restaurantsTypeAttributes: List<RestaurantsSearchAttributes>) {
        this.restaurantsTypeAttributes = restaurantsTypeAttributes
    }
}
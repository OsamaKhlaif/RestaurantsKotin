package com.example.restaurants

import android.os.Parcel
import android.os.Parcelable

class RestaurantsSearchAttributes() : Parcelable {

    lateinit var placeName: String
    var restaurantsReview: Int = 0
    var restaurantsRating: Double = 0.0
    lateinit var restaurantsImage: String
    lateinit var restaurantsDisplayPhone: String
    lateinit var restaurantsZipCode: String
    lateinit var restaurantsAddress: List<String>
    lateinit var restaurantsId: String
    lateinit var restaurantsPhoneCall: String

    constructor(parcel: Parcel) : this() {
        placeName = parcel.readString().toString()
        restaurantsReview = parcel.readInt()
        restaurantsRating = parcel.readDouble()
        restaurantsImage = parcel.readString().toString()
        restaurantsDisplayPhone = parcel.readString().toString()
        restaurantsZipCode = parcel.readString().toString()
        restaurantsAddress = parcel.createStringArrayList()!!
        restaurantsId = parcel.readString().toString()
        restaurantsPhoneCall = parcel.readString().toString()
    }

    constructor(
        placeName: String,
        restaurantsReview: Int,
        restaurantsRating: Double,
        restaurantsImage: String,
        restaurantsDisplayPhone: String,
        restaurantsZipCode: String,
        restaurantsAddress: List<String>,
        restaurantsPhoneCall: String,
        restaurantsId: String
    ) : this() {

        this.placeName = placeName
        this.restaurantsRating = restaurantsRating
        this.restaurantsReview = restaurantsReview
        this.restaurantsImage = restaurantsImage
        this.restaurantsDisplayPhone = restaurantsDisplayPhone
        this.restaurantsZipCode = restaurantsZipCode
        this.restaurantsAddress = restaurantsAddress
        this.restaurantsId = restaurantsId
        this.restaurantsPhoneCall = restaurantsPhoneCall
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(placeName)
        parcel.writeInt(restaurantsReview)
        parcel.writeDouble(restaurantsRating)
        parcel.writeString(restaurantsImage)
        parcel.writeString(restaurantsDisplayPhone)
        parcel.writeString(restaurantsZipCode)
        parcel.writeStringList(restaurantsAddress)
        parcel.writeString(restaurantsId)
        parcel.writeString(restaurantsPhoneCall)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantsSearchAttributes> {
        override fun createFromParcel(parcel: Parcel): RestaurantsSearchAttributes {
            return RestaurantsSearchAttributes(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantsSearchAttributes?> {
            return arrayOfNulls(size)
        }
    }

}
package com.example.restaurants

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.restaurants.API.APIClient
import com.example.restaurants.API.APIInterface
import com.example.restaurants.APIBusinessId.RestaurantsId
import com.example.restaurants.ViewPager.ViewPagerAdapter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class RestaurantsDetailsActivity : AppCompatActivity() {

    private lateinit var restaurantsName: TextView;
    private lateinit var restaurantsAttributes: RestaurantsSearchAttributes
    private lateinit var restaurantsPhone: TextView
    private lateinit var restaurantsAddress: TextView
    private lateinit var restaurantsCallButton: ImageButton
    private lateinit var apiInterface: APIInterface
    private val TAG: String = RestaurantsDetailsActivity::class.java.simpleName
    private lateinit var restaurantImage: ArrayList<String>
    private lateinit var imageViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_of_restaurants)

        restaurantImage = ArrayList()
        restaurantsName = findViewById(R.id.restaurants_name_text_view)
        restaurantsPhone = findViewById(R.id.restaurants_phone_text_view)
        restaurantsAddress = findViewById(R.id.restaurants_address_text_view)
        restaurantsCallButton = findViewById(R.id.restaurants_call_button)
        imageViewPager = findViewById(R.id.restaurants_images_view_pager)

        var intent: Intent = getIntent()
        restaurantsAttributes = intent.getParcelableExtra("details")!!
        findInformationOfRestaurants()
        // when the user click in call button to call the restaurants
        restaurantsCallButton.setOnClickListener {
            var intentCall: Intent = Intent(Intent.ACTION_DIAL)
            intentCall.setData(Uri.parse("tel:" + restaurantsAttributes.restaurantsPhoneCall))
            startActivity(intentCall)

        }

    }

    private fun findInformationOfRestaurants() {

        apiInterface = APIClient().getClient().create(APIInterface::class.java)
        val apiCallId: Observable<RestaurantsId> =
            apiInterface.getRestaurantsId(restaurantsAttributes.restaurantsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())



        apiCallId.subscribe(object : Observer<RestaurantsId> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "Start the subscribe")
            }

            override fun onNext(restaurantId: RestaurantsId) {
                for (position in restaurantId.photos.indices) {
                    restaurantImage.add(restaurantId.photos.get(position))
                }

                restaurantsName.text = ("\nName: " + restaurantsAttributes.placeName
                        + "\n\nRating: " + restaurantsAttributes.restaurantsRating
                        + " Stars\n\nReview: " + restaurantsAttributes.restaurantsReview)

                restaurantsPhone.text = "\nPhone: " + restaurantsAttributes.restaurantsDisplayPhone

                restaurantsAddress.text = ("\nZip Code: " + restaurantsAttributes.restaurantsZipCode
                        + "\n\nAddress: " + displayArray(restaurantsAttributes.restaurantsAddress)
                        + "\n\nStatus: " + (if (restaurantId.hours == null) "--------" else if (restaurantId.hours
                        .get(0).is_open_now == true
                ) "Open Now" else "Close Now") + "\n\n")

                //this command to check if the hours is null or no.
                var viewPagerAdapter: ViewPagerAdapter =
                    ViewPagerAdapter(this@RestaurantsDetailsActivity, restaurantImage)
                imageViewPager.adapter = viewPagerAdapter
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Occur any Error");
            }

            override fun onComplete() {
                Log.d(TAG, "Finish the Process");
            }

        })
    }

    private fun displayArray(data: List<String>): String {

        var dataArray: StringBuilder = StringBuilder()
        for (position in 0 until data.size) {
            if (position != data.size - 1) {
                dataArray.append(data.get(position))
                //to do the address above each other
                dataArray.append(",\n" + "                 ")
            } else {
                dataArray.append(data.get(position))
            }
        }
        return dataArray.toString()
    }

}
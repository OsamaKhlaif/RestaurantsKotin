package com.example.restaurants

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.restaurants.API.APIClient
import com.example.restaurants.API.APIInterface
import com.example.restaurants.APIBusinessSearch.Restaurants
import com.example.restaurants.RecyclerView.RecyclerAdapter
import com.example.restaurants.RecyclerView.RecyclerAdapter.RecyclerViewClickListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var searchButton: ImageButton
    private lateinit var placeSearchEditText: EditText
    private lateinit var placeName: String
    private lateinit var apiInterface: APIInterface
    private lateinit var restaurantsAttributes: RestaurantsSearchAttributes
    private val TAG: String = MainActivity::class.java.simpleName
    private lateinit var restaurantsCostEffective: ArrayList<RestaurantsSearchAttributes>
    private lateinit var restaurantsBitPricier: ArrayList<RestaurantsSearchAttributes>
    private lateinit var restaurantsBigSpender: ArrayList<RestaurantsSearchAttributes>
    private lateinit var listener: RecyclerAdapter.RecyclerViewClickListener
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var item: Items
    private lateinit var homeScreenLinearLayout: LinearLayout
    private lateinit var customRecyclerView: CustomRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeScreenLinearLayout = findViewById(R.id.home_screen_linear_layout)
        searchButton = findViewById(R.id.search_image_button)
        placeSearchEditText = findViewById(R.id.place_search_edit_text)
        searchButton.setOnClickListener {
            placeName = placeSearchEditText.text.toString()
            homeScreenLinearLayout.removeAllViews()
            findRestaurants()
        }
    }

    private fun findRestaurants() {

        restaurantsCostEffective = ArrayList<RestaurantsSearchAttributes>()
        restaurantsBitPricier = ArrayList<RestaurantsSearchAttributes>()
        restaurantsBigSpender = ArrayList<RestaurantsSearchAttributes>()
        apiInterface = APIClient().getClient().create(APIInterface::class.java)


        var apiCall: Observable<Restaurants> = apiInterface.getRestaurants(placeName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        apiCall.subscribe(object : Observer<Restaurants> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "Start the subscribe")
            }

            override fun onNext(restaurant: Restaurants) {

                var position: Int = 0
                var priceUnitCharNumber: Int

                while (position < restaurant.businesses.size) {
                    if (restaurant.businesses.get(position).price != null) {
                        priceUnitCharNumber = restaurant.businesses.get(position).price.length
                    } else {
                        priceUnitCharNumber = 1
                    }

                    if (priceUnitCharNumber == 1) {
                        restaurantsCostEffective.add(setRestaurantsAttributes(restaurant, position))
                    } else if (priceUnitCharNumber == 2) {
                        restaurantsBitPricier.add(setRestaurantsAttributes(restaurant, position))
                    } else if (priceUnitCharNumber == 3) {
                        restaurantsBigSpender.add(setRestaurantsAttributes(restaurant, position))
                    }
                    position++
                }
                if (restaurant.businesses.isNotEmpty()) {
                    addCustomComponents("Cost Effective", restaurantsCostEffective)
                    addCustomComponents("Bit Pricier", restaurantsBitPricier)
                    addCustomComponents("Big Spender", restaurantsBigSpender)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "This " + placeName + " not have any registrar restaurant",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onError(e: Throwable) {

                var connected: Boolean = false
                var connectivityManager: ConnectivityManager =
                    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
                ) {
                    connected = true
                } else {
                    connected = false
                }

                if (connected == false) {
                    Toast.makeText(
                        this@MainActivity,
                        "The Internet connection failed",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Enter the correct City Or this application doesn't cover this City",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onComplete() {
                Log.d(TAG, "Finish the Process")
            }

        })

    }

    private fun setRestaurantsAttributes(
        restaurant: Restaurants,
        position: Int
    ): RestaurantsSearchAttributes {
        restaurantsAttributes = RestaurantsSearchAttributes(
            restaurant.businesses[position].name,
            restaurant.businesses[position].review_count,
            restaurant.businesses[position].rating,
            restaurant.businesses[position].image_url,
            restaurant.businesses[position].display_phone,
            restaurant.businesses[position].location.zip_code,
            restaurant.businesses[position].location.display_address,
            restaurant.businesses[position].phone,
            restaurant.businesses[position].id
        )
        return restaurantsAttributes
    }

    private fun addCustomComponents(
        title: String,
        restaurantsAttributes: List<RestaurantsSearchAttributes>
    ) {
        item = Items(title, restaurantsAttributes)
        setOnClickListener(item)
        recyclerAdapter =
            RecyclerAdapter(this@MainActivity, item.getRestaurantsTypeAttributes(), listener)
        customRecyclerView = CustomRecyclerView(this)
        customRecyclerView.titleTextView.text = item.getTitleText()
        customRecyclerView.recyclerView.adapter = recyclerAdapter
        homeScreenLinearLayout.addView(customRecyclerView)
    }

    private fun setOnClickListener(item: Items) {

        listener = object : RecyclerViewClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(
                    this@MainActivity.applicationContext,
                    RestaurantsDetailsActivity::class.java
                )
                val restaurantsAttributes = RestaurantsSearchAttributes(
                    item.getRestaurantsTypeAttributes()[position].placeName,
                    item.getRestaurantsTypeAttributes()[position].restaurantsReview,
                    item.getRestaurantsTypeAttributes()[position].restaurantsRating,
                    item.getRestaurantsTypeAttributes()[position].restaurantsImage,
                    item.getRestaurantsTypeAttributes()[position].restaurantsDisplayPhone,
                    item.getRestaurantsTypeAttributes()[position].restaurantsZipCode,
                    item.getRestaurantsTypeAttributes()[position].restaurantsAddress,
                    item.getRestaurantsTypeAttributes()[position].restaurantsPhoneCall,
                    item.getRestaurantsTypeAttributes()[position].restaurantsId
                )

                intent.putExtra("details", restaurantsAttributes)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity,
                    customRecyclerView.recyclerView, "recycler_view"
                )
                this@MainActivity.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
}



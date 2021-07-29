package com.example.restaurants.RecyclerView

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.RestaurantsSearchAttributes
import com.squareup.picasso.Picasso

class RecyclerAdapter(
    context: Context,
    restaurantsAttributes: List<RestaurantsSearchAttributes>,
    listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<RecyclerViewHeaven>() {

    private var context: Context = context
    private var restaurantsAttributes: List<RestaurantsSearchAttributes> = restaurantsAttributes
    private val listener: RecyclerViewClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHeaven {
        var view: View = LayoutInflater.from(context).inflate(R.layout.custom_view, parent, false)
        return RecyclerViewHeaven(view, listener)
    }

    override fun onBindViewHolder(holder: RecyclerViewHeaven, position: Int) {
        Picasso.get().load(Uri.parse(restaurantsAttributes[position].restaurantsImage))
            .into(holder.restaurantsImageView)
        holder.restaurantsDescriptionTextView.text = (restaurantsAttributes[position].placeName
                + "\n" + restaurantsAttributes[position].restaurantsRating + " Stars, Review "
                + restaurantsAttributes[position].restaurantsReview)
    }

    override fun getItemCount(): Int {
        return restaurantsAttributes.size
    }

    interface RecyclerViewClickListener {
        fun onClick(v: View, position: Int)
    }


}
package com.example.restaurants.ViewPager

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.squareup.picasso.Picasso

class ViewPagerAdapter(context: Context, restaurantsImage: List<String>) :
    RecyclerView.Adapter<ViewPagerViewHeaven>() {

    private var context: Context = context
    private var restaurantsImage: List<String> = restaurantsImage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHeaven {
        var view: View = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return ViewPagerViewHeaven(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHeaven, position: Int) {
        Picasso.get().load(Uri.parse(restaurantsImage[position])).into(holder.roundedImageView)
    }

    override fun getItemCount(): Int {
        return restaurantsImage.size
    }
}
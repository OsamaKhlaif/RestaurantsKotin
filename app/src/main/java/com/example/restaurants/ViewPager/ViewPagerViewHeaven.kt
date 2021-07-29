package com.example.restaurants.ViewPager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.makeramen.roundedimageview.RoundedImageView

class ViewPagerViewHeaven : RecyclerView.ViewHolder {
    lateinit var roundedImageView: RoundedImageView

    constructor(itemView: View) : super(itemView) {
        roundedImageView = itemView.findViewById(R.id.rounded_image_view)
    }
}
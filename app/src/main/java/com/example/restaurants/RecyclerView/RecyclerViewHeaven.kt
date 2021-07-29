package com.example.restaurants.RecyclerView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R

class RecyclerViewHeaven : RecyclerView.ViewHolder, View.OnClickListener {

    lateinit var restaurantsImageView: ImageView
    lateinit var restaurantsDescriptionTextView: TextView
    private lateinit var listener: RecyclerAdapter.RecyclerViewClickListener

    constructor(itemView: View, listener: RecyclerAdapter.RecyclerViewClickListener) : super(
        itemView
    ) {
        restaurantsImageView = itemView.findViewById(R.id.restaurants_image_view)
        restaurantsDescriptionTextView =
            itemView.findViewById(R.id.restaurants_description_text_view)
        itemView.setOnClickListener(this)
        this.listener = listener
    }

    override fun onClick(v: View) {
        listener.onClick(v, adapterPosition)
    }

}
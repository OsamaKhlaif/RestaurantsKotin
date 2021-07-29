package com.example.restaurants

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView : LinearLayout {

    lateinit var titleTextView: TextView
    lateinit var recyclerView: RecyclerView

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.custom_recycler_view, this)
        titleTextView = findViewById(R.id.title_text_view)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(
            getContext().applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }
}
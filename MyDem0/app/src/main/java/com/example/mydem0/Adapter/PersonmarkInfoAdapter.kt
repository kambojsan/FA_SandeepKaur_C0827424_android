package com.example.mydem0.Adapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.mydem0.Person
import com.example.mydem0.PersonsViewModel
import com.example.mydem0.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class PersonmarkInfoAdapter(val context: Activity) : GoogleMap.InfoWindowAdapter
{// 2
private val contents: View
    // 3
    init {
        contents = context.layoutInflater.inflate(R.layout.markerdisplaycontent, null) }
    // 4
    override fun getInfoWindow(marker: Marker): View? {
        // This function is required, but can return null if
        // not replacing the entire info window
        return null
    }
    // 5
    override fun getInfoContents(marker: Marker): View? {
        val titleView = contents.findViewById<TextView>(R.id.title)
        titleView.text = marker.title ?: ""
        val phoneView = contents.findViewById<TextView>(R.id.subtitle)
        phoneView.text = marker.snippet ?: ""


       var person = marker.tag as   PersonsViewModel.BookmarkView
        // Set imageView bitmap here
       // imageView.setImageBitmap(person.getImage(context))
        return contents
    }
       }
package com.example.mydem0

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person_viewholder.view.*


class recylerviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val noresult = itemView.findViewById(R.id.noresultfound) as TextView
    val name = itemView.findViewById(R.id.nametext) as TextView
   // val gender = itemView.findViewById(R.id.gendertext) as TextView
    val birth = itemView.findViewById(R.id.birthdaytext) as TextView
    val long = itemView.findViewById(R.id.longtext) as TextView
   // val imageView = itemView.findViewById<ImageView>(R.id.imageviewtext) as ImageView


   // var person = marker.tag as   PersonListviewModel.BookmarkView
    // Set imageView bitmap here


    fun Intialise (key : String?,persons : Person?,person: PersonListviewModel.BookmarkView ,action: recylerviewAdapter.OnClickLister, context: Context)
    {
        if (key == null) {
            var person = person as PersonListviewModel.BookmarkView
            name.text = "Place's name = " + person.name
            birth.text = "long ="+person.location.latitude.toString()
           long.text = "lati ="+person.location.longitude.toString()
        }
        else
        {
            var persons = person
            name.text = "Place's name " + person.name
            name.text = "Place's name = " + person.name
            birth.text = "long ="+person.location.latitude.toString()
            long.text = "lati ="+person.location.longitude.toString()

           //
        }
       // imageView.setImageBitmap(person.getImage(context))
        itemView.setOnClickListener(){
            action.onItemClick(person,adapterPosition)

        }
    }

}
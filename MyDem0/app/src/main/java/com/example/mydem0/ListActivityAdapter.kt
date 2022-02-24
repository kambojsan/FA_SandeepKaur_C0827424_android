package com.example.mydem0

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class recylerviewAdapter( var intkey : Int?,var key : String?,private var List : List<Person>,
                             private var Lists : List<PersonListviewModel.BookmarkView>,
                               var clickLister: OnClickLister,val context: Context)
                                  : RecyclerView.Adapter<recylerviewViewHolder>()

  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recylerviewViewHolder {
        // var view = parent

            var view = LayoutInflater.from(parent.context).inflate(
                R.layout.person_viewholder,
                parent, false
            )


        return recylerviewViewHolder(view)

    }


    override fun getItemCount(): Int
    {
        if(intkey != 0){
            return 1
        }
        else {
            if (key == null) {
                return Lists.size

            } else {

                return List.size
            }
        }
    }
    override fun onBindViewHolder(holder: recylerviewViewHolder, position: Int)
    {
        if(intkey != 0)
        {
            holder.noresult.text = "No result found"
        }
        else
         {
            if (key == null) {
                var currentperson = Lists[position]
                holder.Intialise(key, null, currentperson, clickLister, context)
            } else {
                var currentpersons = Lists[position]
                var currentperson = List[position]
                holder.Intialise(key, currentperson, currentpersons, clickLister, context)

            }
        }

//           holder.age.text = (position + 1).toString()
//          holder.date.text =  currentperson.name
//          holder.fees.text = currentperson.fees.toString()
//          holder.name.text = currentperson.id.toString()




    }

    fun Setvalue(searchitem: List<Person>,item : List<PersonListviewModel.BookmarkView>)
    {
        if (key == null){

        Lists = item
            notifyDataSetChanged()
        }
        else
        {
            List = searchitem
            notifyDataSetChanged()
        }


    }

    fun  deletePerson(position: Int) : PersonListviewModel.BookmarkView
    {
        return Lists.get(position)
        notifyDataSetChanged()
    }

    interface OnClickLister
    {
        fun onItemClick(person: PersonListviewModel.BookmarkView, position: Int)

    }
}

package com.example.kotlinepos

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView

class ListElement (private val context:Activity,private val arrayList:ArrayList<Movie>) :ArrayAdapter<Movie>(context,R.layout.list_element_layout,arrayList){




    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var inflatotor:LayoutInflater = LayoutInflater.from(context);
        var rowview:View = inflatotor.inflate(R.layout.list_element_layout,null,true)


        var movieName:TextView = rowview.findViewById(R.id.mID) as TextView
        movieName.setText(arrayList[position].movieName)

        var releaseDate:TextView = rowview.findViewById(R.id.rdID) as TextView
        releaseDate.setText(arrayList[position].realiseDate)

        var director:TextView = rowview.findViewById(R.id.dID) as TextView
        director.setText(arrayList[position].director)

        var cast:TextView = rowview.findViewById(R.id.cID) as TextView
        cast.setText(arrayList[position].cast)

        var genre:TextView = rowview.findViewById(R.id.gID) as TextView
        genre.setText(arrayList[position].genre)

        var rating:RatingBar = rowview.findViewById(R.id.rID) as RatingBar
        rating.rating = arrayList[position].rating!!

        return rowview
    }


}
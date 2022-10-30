package com.example.kotlinepos

import android.content.Context
import android.content.DialogInterface
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

class Alert {

     fun alert(context:Context){
        var alert: AlertDialog.Builder = AlertDialog.Builder(context)
        alert.setMessage("You have to enter a name of the movie!")
            .setPositiveButton("OK", null)

        val a: AlertDialog = alert.create()
        a.show()

    }

    public fun checkValues(movie:Movie):Boolean{
        if(movie.movieName.isNullOrEmpty()){
            return false;
        }
        return true;

    }

    public fun showAlert(movieName: String?,adapter:ArrayAdapter<Movie>,position:Int,context:Context) {

        var alert: AlertDialog.Builder = AlertDialog.Builder(context)
        alert.setMessage("Do you want to delete a movie: " + movieName + " from your movie list?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                adapter.remove(adapter.getItem(position))
                adapter.notifyDataSetChanged()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
            })
        val a:AlertDialog = alert.create()
        a.show()
    }
}
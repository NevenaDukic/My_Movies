package com.example.kotlinepos

import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class SecondActivity : AppCompatActivity() {

    lateinit var adapter:ArrayAdapter<String>

    lateinit var spinner:Spinner

    lateinit var rating:RatingBar;
    lateinit var movie:Movie;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        movie = Movie();


        setSpinner();

        initAction()



    }




    private fun initAction(){
        var btn = findViewById<Button>(R.id.confirmMovie);
        btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                getMovie()
                var alert = Alert()
                if(!alert.checkValues(movie)){
                    alert.alert(this@SecondActivity)
                }else {
                    var i = Intent(this@SecondActivity, MainActivity::class.java);
                    i.putExtra("Movie", movie)
                    setResult(RESULT_OK, i)
                    finish()
                }
            }
        })

        var btn1 = findViewById<Button>(R.id.btnBack);
        btn1.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var i = Intent(this@SecondActivity,MainActivity::class.java);
                startActivity(i)
            }
        })


    }
    fun setSpinner(){
        var genres = arrayListOf<String>("Romantic","Action","Sci-fi","Adventure","Thriler","Drama","Comedy","Musical");
        spinner = findViewById<Spinner>(R.id.GenreFiledId);
        adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter;

    }




    fun getMovie(){

        movie.movieName = (findViewById(R.id.movieNameFieldId) as EditText).text.toString();
        movie.realiseDate = (findViewById(R.id.DateFieldId) as EditText).text.toString()
        movie.director = (findViewById(R.id.DirectorFieldId) as EditText).text.toString();
        movie.cast = (findViewById(R.id.CastFiledID) as EditText).text.toString()
        rating = findViewById(R.id.ratingBar);
        movie.rating = rating!!.rating
        var spinner = findViewById(R.id.GenreFiledId) as Spinner;
        movie.genre = spinner.selectedItem.toString()
    }




}
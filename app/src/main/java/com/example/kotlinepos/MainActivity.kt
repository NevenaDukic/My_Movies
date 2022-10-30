package com.example.kotlinepos

import android.content.Context
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var addButton:Button?=null
    lateinit var movie:Movie;

    private var check = 0;
    private var position = 0;

    lateinit var adapter:ArrayAdapter<Movie>
    lateinit var movieArray:ArrayList<Movie>
    lateinit var listMovie:ListView

    lateinit var spinner:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listMovie = findViewById(R.id.main_listView)
        movieArray = ArrayList<Movie>()
        readData(applicationContext)
        adapter = ListElement(this,movieArray);
        listMovie.adapter = adapter

        initAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addID->{
                val i = Intent(this@MainActivity,SecondActivity::class.java);
                startActivityForResult(i,1)
            }
            R.id.deleteID->{
                if (check==1) {
                    Alert().showAlert(adapter.getItem(position)?.movieName,adapter,position,this)
                    check = 0;
                }
            }
            R.id.edit_id->{
                if (check==1){
                    var checkedMovie = adapter.getItem(position) as Movie
                    if (checkedMovie!=null) {
                        edit(checkedMovie,position)
                    }
                    check = 0;
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun edit(movie:Movie,position:Int) {

        var alert = AlertDialog.Builder(this)
        alert.setTitle("Edit watched movie:")
        var dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        alert.setView(dialogView)
        alert.setPositiveButton("SAVE",null)

        setEditValues(dialogView,movie)

        val customDialog= alert.create()
        customDialog.show()

        customDialog.getButton(BUTTON_POSITIVE).setOnClickListener(View.OnClickListener {
            var movie1 = getEditInformation(dialogView) as Movie
            var a = Alert()
            if (!a.checkValues(movie1)){
                a.alert(this@MainActivity)
            }else {
                addMovie(movie1, position)
                customDialog.dismiss()
            }
        })

    }

    private fun setEditValues(dialogView:View,movie:Movie){
        var name = dialogView.findViewById<EditText>(R.id.movieName_edit_id)
        name.setText(movie.movieName)

        var director = dialogView.findViewById(R.id.director_edit_id) as EditText
        director.setText(movie.director)

        var cast = dialogView.findViewById(R.id.cast_edit_id) as EditText
        cast.setText(movie.cast)

        var releaseDate = dialogView.findViewById(R.id.releaseDate_edit_id) as EditText
        releaseDate.setText(movie.realiseDate)

        setSpinner(dialogView,movie)

        var raiting = dialogView.findViewById(R.id.ratingBar_edit_id) as RatingBar
        raiting.rating = movie.rating!!
    }
    private fun addMovie(movie:Movie,positionL:Int){
        adapter.getItem(position)?.movieName = movie.movieName.toString()
        adapter.getItem(position)?.director = movie.director.toString()
        adapter.getItem(position)?.cast = movie.cast.toString()
        adapter.getItem(position)?.rating = movie.rating!!.toFloat()
        adapter.getItem(position)?.genre = movie.genre.toString()
        adapter.getItem(position)?.realiseDate = movie.realiseDate.toString()
        adapter.notifyDataSetChanged()
    }
    private fun getEditInformation(view:View): Movie {

        var movie = Movie()
        movie.movieName = view.findViewById<EditText>(R.id.movieName_edit_id).text.toString()
        movie.director = view.findViewById<EditText>(R.id.director_edit_id).text.toString()
        movie.cast = view.findViewById<EditText>(R.id.cast_edit_id).text.toString()
        movie.realiseDate = view.findViewById<EditText>(R.id.releaseDate_edit_id).text.toString()
        movie.genre = view.findViewById<Spinner>(R.id.spinner_edit_id).selectedItem.toString()
        movie.rating = view.findViewById<RatingBar>(R.id.ratingBar_edit_id).rating
        return movie

    }
    fun setSpinner(view:View,movie:Movie){
        var genres = arrayListOf<String>("Romantic","Action","Sci-fi","Adventure","Thriler","Drama","Comedy","Musical");
        spinner = view.findViewById<Spinner>(R.id.spinner_edit_id);
        var a = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,genres)
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = a;
        spinner.setSelection(a.getPosition(movie.genre.toString()))

    }


    private fun initAction(){

        listMovie.setOnItemClickListener { adapterView, view, position, l ->
            check = 1;
            this.position = position
        }
        addButton = findViewById<Button>(R.id.addMovieButton)

        addButton?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val i = Intent(this@MainActivity,SecondActivity::class.java);
                startActivityForResult(i,1)
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("USAO U REQUEST CODE!")
        if (requestCode==1){
            if (data != null) {
                getInformaton(data)
            }
        }
    }
    private fun getInformaton(intent:Intent){

        movie = Movie();
        movie = intent.getSerializableExtra("Movie") as Movie
        println("Movie je: " + movie.movieName.toString())
        movieArray.add(movie)
        adapter.notifyDataSetChanged()

    }



    private fun saveData(context:Context){
        val gson = Gson();
        val gsonString = gson.toJson(movieArray);

        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("movieList",gsonString)
        editor.apply()
    }

    private fun readData(context:Context){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val gsonString = pref.getString("movieList","")

        val gson = Gson()
        if (gsonString!=""){
            movieArray = gson.fromJson(gsonString, object: TypeToken<ArrayList<Movie>>(){}.type)
        }

    }
    override fun onDestroy() {
        saveData(applicationContext)
        super.onDestroy()

    }

}
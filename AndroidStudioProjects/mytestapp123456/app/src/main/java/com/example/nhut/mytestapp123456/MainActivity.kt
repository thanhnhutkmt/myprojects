package com.example.nhut.mytestapp123456

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message.text = "Test textview kotlin"
        niceToast("Hello")
        message.postDelayed(Runnable {
            niceToast(message = "Hello1", length = Toast.LENGTH_LONG);
        }, 5000);

        val forecastList : RecyclerView = findViewById(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        val items = listOf(
             "Mon  6/23  -  Sunny  -  31/17",
             "Tue  6/24  -  Foggy  -  21/8",
             "Wed  6/25  -  Cloudy  -  22/17",
             "Thurs  6/26  -  Rainy  -  18/11",
             "Fri  6/27  -  Foggy  -  21/10",
             "Sat  6/28  -  TRAPPED  IN  WEATHERSTATION  -  23/18",
             "Sun  6/29  -  Sunny  -  20/7"
        )
        forecastList.adapter = ForecastListAdapter(items)
        val c : Char = 'c'
        val i : Int = c.toInt()
        Log.d("Nhựt", "My log print out $c")
        Log.d("Nhựt", "My log print out $i")
        val s = "Example"
        for (c in s) Log.d("Nhựt", "My log print out $c")
    }

    fun niceToast(message: String,
        tag: String = MainActivity::class.java.simpleName,
        length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, "[$tag] _ " + message, length).show();
    }
}

package com.example.youtubeplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_standalone.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //Wizard när du lägger till t ex Empty Activity gör 3 saker. 1. Kodfil, 2. Layout fil, 3. Manifest. (android.intent.action...)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSingle.setOnClickListener(this)
        btnStandalone.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {     //Intent är en javaclass, därför skriver vi ::class.java. You can not pass kotlin class reference to java (Intent i detta fallet).
                                            //double colon is called a class literal and its a way to pass a reference to a class as a parameter!
                                            //We need to pass an object of type class, whose value is the YouTubeActivity class, and thats how you do it in kotlin.
                                            //You wouldnt pass Int, or String, or Double as a parameter, you would use "Tim" or 77.7. This is the same thing.
            R.id.btnSingle -> Intent(this, YoutubeActivity::class.java)
            R.id.btnStandalone -> Intent(this, StandaloneActivity::class.java)
            else -> throw IllegalArgumentException("Undefined Button Click")
        }
        startActivity(intent)

    }
}


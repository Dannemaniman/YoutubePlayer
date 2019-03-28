package com.example.youtubeplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone.*
import java.lang.IllegalArgumentException

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone)


//        //Metod 1 för listener
//        btnPlayVideo.setOnClickListener(object: View.OnClickListener {
//           override fun onClick(v: View?) {
//           }
//        })
//
//        //Metod 2 för listener, ok om det bara är listener för 1 button
//        btnPlayVideo.setOnClickListener(View.OnClickListener { v ->
//
//        })
//
//        //Metod 3, gör listener till en variabel! Bra vid många knappar
//        val listener = View.OnClickListener { v ->
//
//        }
//        btnPlayVideo.setOnClickListener(listener)
//        btnPlaylist.setOnClickListener(listener)
//      //Metod 4, lägg till interface i class definition och bara lägg till den utan några problem! Du får en OnClick func då också

        btnPlayVideo.setOnClickListener(this)
        btnPlaylist.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        //GoogleDock: "An Intent is a messaging object you can use to request an action from another app component."
        //Intents kan använda för att starta activity, skicka data till en mottagande component som är registered till att få broadcasts
        //det kan också starta en extern service (pdf filen på GAAndroid appen)
        //"So if you wanted to start the devices email app, you just specify email in the intent, without having to know which app the users installed
        //to handle emails on that device"
        val intent = when (view.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_VIDEO_ID, 0, true, false)
            R.id.btnPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_PLAYLIST, 0, 0, true, true)
            else -> throw IllegalArgumentException("Undefined Button Clicked")
        }
        startActivity(intent)
    }
}
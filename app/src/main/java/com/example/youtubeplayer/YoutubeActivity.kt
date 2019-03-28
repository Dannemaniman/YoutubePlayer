package com.example.youtubeplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

//vid användning av const måste constanten vara vid top level, det är hela grejen med varför man använder const.
// den har redan ett värde innan classes instantiatas. Men innan en update för ett tag sedan kunde man inte sätta dem vid top level,
//för att även om dem var marked private så kunde dem anropas i andra classer, alltså fick man isåfall döpa TAG, TAG2, TAG3... etc, vilket är dåligt.

const val YOUTUBE_VIDEO_ID = "393C3pr2ioY"
const val YOUTUBE_PLAYLIST = "PLQl8zBB7bPvIooDTYwY7v_V12KJfQueXZ"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "Youtube Activity"
    private val DIALOG_REQUEST_CODE = 1

    val playerView by lazy { YouTubePlayerView(this) }  //Har inte tillgång till vyn innan onCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_youtube)
        //val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        //kan skriva root = null pga att detta är vår rootView, alltså kan vi skicka in null, annars hade det varit typ parent.
        setContentView(layout)

        //Skapa widget i kod
//        val button1 = Button(this)
//        //Ger den contexten så den vet om environmenten den är i. this = denna activityn, och det kan jag göra pga en activity är en context.
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        button1.text = "Button Added"
//        layout.addView(button1)


        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)
        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }
    //enum är ett sätt att gruppera constanter tillsammans till en type men kan ju också innehålla kod också

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: YoutubePlayer is ${youTubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized Youtube Player Successfully", Toast.LENGTH_SHORT).show()


        //Skapade listenerfunctioner som en variabel så det blir mycket enklare och snyggare!
        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            youTubePlayer?.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, initializationResult: YouTubeInitializationResult?) {

        //egentligen enligt god programmeringsstruktur ska man inte jämföra booleans med om dem är lika med true eller false
        //men här gör jag det pga det är en nullable och den kan vara null, därför är det ok!
        if(initializationResult?.isUserRecoverableError == true) {
            initializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the youtube player ($initializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {

        override fun onSeekTo(p0: Int) {

        }

        override fun onBuffering(p0: Boolean) {

        }
        //this@YoutubeActivity = Du går ut från locala scopet och anropar this på youtubeActivityn, annars hade this varit något annat
        //Det är samma som när du anropar en yttre for loop från den inre och använder @outerForLoop!
        override fun onPlaying() {
           Toast.makeText(this@YoutubeActivity, "Good, video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped", Toast.LENGTH_SHORT).show()

        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click ad now", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Congratulations, video done", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with responsecode $resultCode for request $requestCode")

        if (requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
        }
    }

    //startOnActivity... skickar tillbaka requestCoden till onActivityResult i den activityn jag lämnade.
}
